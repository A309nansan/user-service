package site.nansan.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User_Child")
@Getter
@Setter
public class UserChild {

    @EmbeddedId
    private UserChildId id;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 편의 메소드, 복합 키 도입으로 인한 코드 변경 최소화
    public void setUserId(Long userId) {
        if (this.id == null) {
            this.id = new UserChildId();
        }
        this.id.setUserId(userId);
    }

    public void setChildId(Long childId) {
        if (this.id == null) {
            this.id = new UserChildId();
        }
        this.id.setChildId(childId);
    }

    public Long getUserId() {
        return id != null ? id.getUserId() : null;
    }

    public Long getChildId() {
        return id != null ? id.getChildId() : null;
    }
}