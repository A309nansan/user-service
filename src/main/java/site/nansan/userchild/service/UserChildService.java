package site.nansan.userchild.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import site.nansan.user.domain.Role;
import site.nansan.userchild.domain.UserChild;
import site.nansan.userchild.repository.UserChildRepository;

@Service
@RequiredArgsConstructor
public class UserChildService {

    private final UserChildRepository userChildRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserChildService.class);

    /**
     * 사용자와 자녀 간의 관계 추가
     * @param userId 사용자 ID
     * @param childId 자녀 ID
     * @param role 관계 역할 (PARENT, TEACHER)
     * @return 생성된 관계 객체
     */
    public UserChild addRelationship(Long userId, Long childId, Role role) {
        // 이미 같은 관계가 있는지 확인
        if (userChildRepository.existsById_UserIdAndId_ChildId(userId, childId)) {
            return userChildRepository.findById_UserIdAndId_ChildId(userId, childId);
        }

        UserChild userChild = new UserChild();
        userChild.updateUserChildId(userId, childId);
        userChild.updateRole(role);

        logger.info("Added new relationship: user {} -> child {} with role {}", userId, childId, role);
        return userChildRepository.save(userChild);
    }

    // 나머지 메서드는 동일하게 유지
    public void removeRelationship(Long userId, Long childId) {
        userChildRepository.deleteById_UserIdAndId_ChildId(userId, childId);
    }

    public boolean hasRelationship(Long userId, Long childId, Role role) {
        UserChild userChild = userChildRepository.findById_UserIdAndId_ChildId(userId, childId);
        return userChild != null && role.equals(userChild.getRole());
    }
}