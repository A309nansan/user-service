package site.nansan.userchild.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserChildId implements Serializable {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    // equals()는 두 객체의 모든 필드가 같아야 true 반환
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserChildId)) return false;
        UserChildId that = (UserChildId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(childId, that.childId);
    }

    // hashCode()는 equals와 일치하도록 구성
    @Override
    public int hashCode() {
        return Objects.hash(userId, childId);
    }
}
