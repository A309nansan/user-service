package site.nansan.userchild.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.nansan.user.domain.Role;

@Entity
@Table(name = "user_child")
@Getter
@NoArgsConstructor
public class UserChild {

    @EmbeddedId
    private UserChildId id;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 복합키 생성 및 관리를 위한 편의 메서드
    public void updateUserChildId(Long userId, Long childId) {
        if (this.id == null) {
            this.id = new UserChildId();
        }
        this.id.setUserId(userId);
        this.id.setChildId(childId);
    }

    // Role을 설정하는 메서드 추가
    public void updateRole(Role role) {
        this.role = role;
    }

    // 생성자 추가 (필요한 경우)
    public UserChild(UserChildId id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Long getUserId() {
        return id != null ? id.getUserId() : null;
    }

    public Long getChildId() {
        return id != null ? id.getChildId() : null;
    }
}