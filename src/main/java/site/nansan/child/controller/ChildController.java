package site.nansan.child.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nansan.child.domain.Child;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.Users;
import site.nansan.user.dto.TeacherCodeRequest;
import site.nansan.child.service.ChildService;
import site.nansan.user.service.TeacherCodeService;
import site.nansan.userchild.service.UserChildService;
import site.nansan.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
public class ChildController {

    private final UserService userService;
    private final TeacherCodeService teacherCodeService;
    private final ChildService childService;
    private final UserChildService userChildService;

    /**
     * 자녀 계정에서 선생님 코드로 관계 추가
     * @param codeRequestDto 선생님 코드 정보
     * @return 성공 여부 또는 오류 응답
     */
    @PostMapping("/teacherCode")
    public ResponseEntity<?> addTeacherByCode(@RequestBody TeacherCodeRequest codeRequestDto) {
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
    @DeleteMapping("/teacher/{teacherId}")
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

    /**
     * 자녀 선택 - 자녀 ID를 확인하고 유효성 검사만 수행
     * @param childId 선택한 자녀 ID
     * @return 성공 여부 또는 오류 응답
     */
    @GetMapping("/select/{childId}")
    public ResponseEntity<?> selectChild(@PathVariable Long childId) {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).body("인증되지 않은 사용자입니다");
        }

        // 자녀와 사용자의 관계 확인 (부모나 선생님만 접근 가능)
        List<Long> userChildIds = childService.getChildListByUserId(authenticatedUser.getId())
                .stream()
                .map(Child::getId)
                .toList();

        if (!userChildIds.contains(childId)) {
            return ResponseEntity.status(403).body("접근 권한이 없는 자녀입니다");
        }

        // 자녀 정보 조회
        Child selectedChild = childService.getChildById(childId);
        if (selectedChild == null) {
            return ResponseEntity.status(404).body("자녀 정보를 찾을 수 없습니다");
        }

        // 헤더에 자녀 ID 포함
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Child-Id", childId.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body(selectedChild);
    }
}