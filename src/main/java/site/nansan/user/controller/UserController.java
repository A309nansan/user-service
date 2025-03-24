package site.nansan.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nansan.user.domain.Child;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.Users;
import site.nansan.user.dto.ChildRequestDto;
import site.nansan.user.dto.TeacherCodeRequestDto;
import site.nansan.user.service.ChildService;
import site.nansan.user.service.TeacherCodeService;
import site.nansan.user.service.UserChildService;
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

    // 아래부터 추가한 코드
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
     * @param childRequestDto 추가할 자녀 정보
     * @return 추가된 자녀 정보 또는 오류 응답
     */
    @PostMapping("/parent/child")
    public ResponseEntity<?> addChild(@RequestBody ChildRequestDto childRequestDto) {

        // todo : 공통 로직은 상위 클래스에서 호출.
        //        Users user = userService.getAuthenticatedUser();
        ResponseEntity<?> validationResult = validateParentRole();

        if (validationResult.getStatusCode().isError()) {
            return validationResult;
        }

        Users authenticatedUser = (Users) validationResult.getBody();

        // 자녀 추가 로직
        Child newChild = childService.createChild(childRequestDto);
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

    /**
     * 자녀 계정에서 선생님 코드로 관계 추가
     * @param codeRequestDto 선생님 코드 정보
     * @return 성공 여부 또는 오류 응답
     */
    @PostMapping("/child/teacherCode")
    public ResponseEntity<?> addTeacherByCode(@RequestBody TeacherCodeRequestDto codeRequestDto) {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        // 자녀 정보 확인
        Child child = childService.getChildByUserId(authenticatedUser.getId());
        if (child == null) {
            return ResponseEntity.status(404).body("자녀 계정을 찾을 수 없습니다");
        }

        // 코드 검증
        String code = codeRequestDto.getCode();
        if (!teacherCodeService.validateTeacherCode(code)) {
            return ResponseEntity.badRequest().body("유효하지 않은 선생님 코드입니다");
        }

        // 코드로 선생님 ID 찾기
        Long teacherId = teacherCodeService.findTeacherIdByCode(code);
            if (teacherId == null) {
                return ResponseEntity.badRequest().body("코드에 해당하는 선생님을 찾을 수 없습니다");
        }

        // 이미 선생님이 추가되어있는지 확인
        if (userChildService.hasRelationship(teacherId, child.getId(), Role.TEACHER)) {
            return ResponseEntity.badRequest().body("이미 해당 선생님이 추가되어 있습니다");
        }

        // 관계 추가
        userChildService.addRelationship(teacherId, child.getId(), Role.TEACHER);

        return ResponseEntity.ok().body("선생님이 추가되었습니다");
    }

    /**
     * 자녀 계정에서 특정 선생님과의 관계 삭제
     * @param teacherId 삭제할 선생님 ID
     * @return 성공 여부 또는 오류 응답
     */
    @DeleteMapping("/child/teacher/{teacherId}")
    public ResponseEntity<?> removeTeacherRelationship(@PathVariable Long teacherId) {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        // 자녀 정보 확인
        Child child = childService.getChildByUserId(authenticatedUser.getId());
        if (child == null) {
            return ResponseEntity.status(404).body("자녀 계정을 찾을 수 없습니다");
        }

        // 관계가 존재하는지 확인
        if (!userChildService.hasRelationship(teacherId, child.getId(), Role.TEACHER)) {
            return ResponseEntity.status(404).body("해당 선생님과의 연결이 존재하지 않습니다");
        }

        // 관계 삭제
        userChildService.removeRelationship(teacherId, child.getId());

        return ResponseEntity.ok().body("선생님과의 연결이 삭제되었습니다");
    }
}