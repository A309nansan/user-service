package site.nansan.child.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.nansan.child.domain.Child;
import site.nansan.child.dto.ChildRequest;
import site.nansan.child.repository.ChildRepository;
import site.nansan.userchild.repository.UserChildRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;
    private final UserChildRepository userChildRepository;

    /**
     * 새로운 자녀 정보 생성
     * @param childRequest 자녀 정보 요청 객체
     * @return 생성된 자녀 객체
     */
    public Child createChild(ChildRequest childRequest) {
        Child child = Child.builder()
                .name(childRequest.getName())
                .profileImageUrl(childRequest.getProfileImageUrl())
                .birthDate(childRequest.getBirthDate())
                .grade(childRequest.getGrade())
                .gender(childRequest.getGender())
                .build();

        return childRepository.save(child);
    }
    /**
     * 사용자 ID로 연결된 자녀 정보 조회
     * @param userId 사용자 ID
     * @return 자녀 객체 또는 null
     */
    public Child getChildByUserId(Long userId) {
        // User_Child 테이블에서 해당 userId와 연관된 childId 찾기
        Long childId = userChildRepository.findChildIdByUserId(userId);

        if (childId == null) {
            return null;
        }

        return childRepository.findById(childId).orElse(null);
    }

    /**
     * 사용자의 모든 자녀 목록 조회
     * @param userId 사용자 ID
     * @return 자녀 목록
     */
    public List<Child> getChildListByUserId(Long userId) {
        List<Long> childIds = userChildRepository.findChildIdsByUserId(userId);
        return childRepository.findAllByIdIn(childIds);
    }

    public Child getChildById(Long childId) {
        return childRepository.findById(childId).orElse(null);
    }
}