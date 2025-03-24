package site.nansan.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.security.AuthProvider;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

//    @Size(min = 8, max = 50, message = "소셜 로그인 id는 최소 8자 이상, 최대 50자 이하 입니다.")
    @Column(name = "platform_id", nullable = false, unique = true)
    private String platformId;

//    @Size(max = 30, message = "이메일은 최대 30자까지 가능합니다.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

//    @Size(min = 3, max = 30, message = "이름은 3자 이상, 30자 이하여야 합니다.")
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickName;

    @Column(name = "social_platform", length = 20)
    private SocialPlatform socialPlatform;

    @Column(name="role", length=10)
    private Role role;


    @Builder
    public Users(String platformId, String email, String nickName, SocialPlatform socialPlatform, Role role) {
        this.platformId = platformId;
        this.email = email;
        this.nickName = nickName;
        this.socialPlatform = socialPlatform;
        this.role = role;
    }

    public Users(Long id, String nickName, Role role) {
        this.id = id;
        this.nickName = nickName;
        this.role = role;
    }


}
