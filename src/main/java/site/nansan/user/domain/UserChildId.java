package site.nansan.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChildId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "child_id")
    private Long childId;
}