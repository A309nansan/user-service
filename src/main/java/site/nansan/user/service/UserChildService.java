package site.nansan.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.UserChild;
import site.nansan.user.repository.UserChildRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChildService {

    private final UserChildRepository userChildRepository;

    /**
     * 사용자와 자녀 간의 관계 추가
     * @param userId 사용자 ID
     * @param childId 자녀 ID
     * @param role 관계 역할 (PARENT, TEACHER)
     * @return 생성된 관계 객체
     */
    public UserChild addRelationship(Long userId, Long childId, Role role) {
        // 이미 같은 관계가 있는지 확인
        if (userChildRepository.existsByUserIdAndChildId(userId, childId)) {
            log.info("Relationship already exists between user {} and child {}", userId, childId);
            return userChildRepository.findByUserIdAndChildId(userId, childId);
        }

        UserChild userChild = new UserChild();
        userChild.setUserId(userId);
        userChild.setChildId(childId);
        userChild.setRole(role);

        log.info("Added new relationship: user {} -> child {} with role {}", userId, childId, role);
        return userChildRepository.save(userChild);
    }

    /**
     * 사용자와 자녀 간의 관계 삭제
     * @param userId 사용자 ID
     * @param childId 자녀 ID
     */
    public void removeRelationship(Long userId, Long childId) {
        userChildRepository.deleteByUserIdAndChildId(userId, childId);
        log.info("Removed relationship: user {} -> child {}", userId, childId);
    }

    /**
     * 특정 역할로 사용자와 자녀 간의 관계가 있는지 확인
     * @param userId 사용자 ID
     * @param childId 자녀 ID
     * @param role 확인할 역할
     * @return 관계 존재 여부
     */
    public boolean hasRelationship(Long userId, Long childId, Role role) {
        UserChild userChild = userChildRepository.findByUserIdAndChildId(userId, childId);
        return userChild != null && role.equals(userChild.getRole());
    }
}