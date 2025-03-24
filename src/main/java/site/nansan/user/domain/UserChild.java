package site.nansan.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
// todo : 네이밍 컨벤션인데 대문자를 넣을거면 언더바(_)를 넣지 않음
// UserChild 나 user_child나 이렇게 해야지 User_Child 는 별로.
// 근데 이건 테이블 명이니 user_child를 넣는다.
@Table(name = "User_Child")
@Getter
@Setter
public class UserChild {

    @EmbeddedId
    private UserChildId id;

    @Enumerated(EnumType.STRING)
    private Role role;

    /* todo : 복합키라 setter가 필요할 수 있음(나도모름)
        하지만 setId는 매우 매우 치명적이기 때문에 보통 이런 이름으로 하지 않는다.
        이럴 때는 주석으로 설명을 넣고, setter라고 이름을 짓지 않고 add나 이런 누가봐도
        특별한 이름을 넣어서 이놈 쉽게 바꾸면 안되겠다는 인식을 줘야 합니다.
     */
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

    // 좋아요.
    public Long getUserId() {
        return id != null ? id.getUserId() : null;
    }

    public Long getChildId() {
        return id != null ? id.getChildId() : null;
    }
}