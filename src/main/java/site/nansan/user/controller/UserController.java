package site.nansan.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nansan.child.domain.Child;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.Users;
import site.nansan.child.dto.ChildRequest;
import site.nansan.child.service.ChildService;
import site.nansan.user.dto.RoleUpdateRequest;
import site.nansan.user.dto.UserDto;
import site.nansan.user.service.TeacherCodeService;
import site.nansan.userchild.service.UserChildService;
import site.nansan.user.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController implements UserSwaggerController {

    private final UserService userService;
    private final TeacherCodeService teacherCodeService;
    private final ChildService childService;
    private final UserChildService userChildService;

    @Override
    public ResponseEntity<Users> getAuthenticatedUser() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(authenticatedUser);
    }

    @Override
    public ResponseEntity<UserDto> getAuthenticatedUser(Long userId) {

        UserDto authenticatedUser = userService.getAuthenticatedUser(userId);

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(authenticatedUser);
    }

    // 아래부터 추가한 코드
    /**
     * 일반 사용자 권한 확인
     * @return 권한이 있는 인증된 사용자 또는 권한 오류 응답
     */
    private ResponseEntity<?> validateUserRole() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build(); // 인증되지 않음
        }

        if (!Role.USER.equals(authenticatedUser.getRole())) {
            return ResponseEntity.status(403).build(); // 권한 없음
        }

        return ResponseEntity.ok(authenticatedUser); // 권한 확인 완료
    }

    /**
     * 선생님 권한 확인
     * @return 권한이 있는 인증된 사용자 또는 권한 오류 응답
     */
    private ResponseEntity<?> validateTeacherRole() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        if (!Role.TEACHER.equals(authenticatedUser.getRole())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(authenticatedUser);
    }

    /**
     * 부모 권한 확인
     * @return 권한이 있는 인증된 사용자 또는 권한 오류 응답
     */
    private ResponseEntity<?> validateParentRole() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        if (!Role.PARENT.equals(authenticatedUser.getRole())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(authenticatedUser);
    }

    /**
     * 선생님 코드 생성
     * 사용자 role이 TEACHER일 경우에만 코드 생성
     * @return 생성된 코드 또는 오류 응답
     */
    // todo : Override 해야됨.
    // todo : Interface에서 mapping 주소 넣기. api 주소 중복됨!
    @PostMapping("/teacher/code")
    @Override
    public ResponseEntity<?> generateTeacherCode() {
        ResponseEntity<?> validationResult = validateTeacherRole();

        if (validationResult.getStatusCode().isError()) {
            return validationResult;
        }

        Users authenticatedUser = (Users) validationResult.getBody();
        String teacherCode = teacherCodeService.generateTeacherCode(authenticatedUser.getId());

        // 응답 생성
        Map<String, String> response = new HashMap<>();
        response.put("code", teacherCode);

        return ResponseEntity.ok(response);
    }

    /**
     * 선생님 코드 조회
     * 사용자 role이 TEACHER일 경우에만 코드 조회
     * @return 조회된 코드 또는 오류 응답
     */
    @GetMapping("/teacher/code")
    public ResponseEntity<?> getTeacherCode() {
        ResponseEntity<?> validationResult = validateTeacherRole();

        if (validationResult.getStatusCode().isError()) {
            return validationResult;
        }

        Users authenticatedUser = (Users) validationResult.getBody();
        String teacherCode = teacherCodeService.getTeacherCode(authenticatedUser.getId());

        if (teacherCode == null) {
            return ResponseEntity.notFound().build();
        }

        // 응답 생성
        Map<String, String> response = new HashMap<>();
        response.put("code", teacherCode);

        return ResponseEntity.ok(response);
    }

    /**
     * 자녀 추가
     * @param childRequest 추가할 자녀 정보
     * @return 추가된 자녀 정보 또는 오류 응답
     */
    @PostMapping("/parent/child")
    public ResponseEntity<?> addChild(@RequestBody ChildRequest childRequest) {


        ResponseEntity<?> validationResult = validateParentRole();



        if (validationResult.getStatusCode().isError()) {
            return validationResult;
        }

        Users authenticatedUser = (Users) validationResult.getBody();

        // 자녀 추가 로직
        Child newChild = childService.createChild(childRequest);
        userChildService.addRelationship(authenticatedUser.getId(), newChild.getId(), Role.PARENT);

        return ResponseEntity.ok(newChild);
    }

    /**
     * 부모의 자녀 목록 조회
     * @return 자녀 목록 또는 오류 응답
     */
    @GetMapping("/parent/childList")
    public ResponseEntity<?> getChildList() {
        ResponseEntity<?> validationResult = validateParentRole();

        if (validationResult.getStatusCode().isError()) {
            return validationResult;
        }

        Users authenticatedUser = (Users) validationResult.getBody();
        List<Child> childList = childService.getChildListByUserId(authenticatedUser.getId());

        return ResponseEntity.ok(childList);
    }



    @GetMapping("/user/detail-status")
    public ResponseEntity<?> getUserDetailStatus() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build(); // 인증 실패
        }

        Map<String, Boolean> response = new HashMap<>();
        Boolean detailStatus = authenticatedUser.getDetailStatus();
        response.put("detailStatus", detailStatus != null ? detailStatus : false);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user/update-role")
    public ResponseEntity<?> upgradeUserRole(@RequestBody RoleUpdateRequest request) {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        // detailStatus가 true면 거부
        if (authenticatedUser.getDetailStatus()) {
            return ResponseEntity.status(400).body("이미 detailStatus가 true입니다.");
        }

        Role newRole = request.getNewRole();
        if (newRole != Role.TEACHER && newRole != Role.PARENT) {
            return ResponseEntity.status(400).body("role은 TEACHER 또는 PARENT만 가능합니다.");
        }

        // 역할과 상태 업데이트
        authenticatedUser.updateRole(newRole);
        authenticatedUser.setDetailStatus(true);
        userService.saveUser(authenticatedUser); // 또는 updateUser(authenticatedUser)

        Map<String, Object> response = new HashMap<>();
        response.put("id", authenticatedUser.getId());
        response.put("newRole", authenticatedUser.getRole());
        response.put("detailStatus", authenticatedUser.getDetailStatus());

        return ResponseEntity.ok(response);
    }

}