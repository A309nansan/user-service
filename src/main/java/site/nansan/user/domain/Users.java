package site.nansan.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    // @Size(min = 8, max = 50, message = "소셜 로그인 id는 최소 8자 이상, 최대 50자 이하 입니다.")
    @Column(name = "platform_id", nullable = false, unique = true)
    private String platformId;

    @Column(name = "social_platform", length = 20)
    private SocialPlatform socialPlatform;

    // @Size(max = 30, message = "이메일은 최대 30자까지 가능합니다.")
    @Column(name = "email", nullable = false, unique = false)
    private String email;

    // @Size(min = 3, max = 30, message = "이름은 3자 이상, 30자 이하여야 합니다.")
    @Column(name = "nickname", nullable = false, unique = false)
    private String nickName;

    @Column(name="role", length=10)
    private Role role;

    @Column(name = "detail_status", nullable = false)
    private Boolean detailStatus;

    @Column(name = "hash_id", length = 100)
    private String hashId;

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;


    @Builder
    public Users(String platformId, SocialPlatform socialPlatform, String email,
                 String nickName, Role role, Boolean detailStatus,
                 String hashId, String profileImageUrl) {
        this.platformId = platformId;
        this.socialPlatform = socialPlatform;
        this.email = email;
        this.nickName = nickName;
        this.role = role;
        this.detailStatus = detailStatus != null ? detailStatus : false;
        this.hashId = hashId;
        this.profileImageUrl = profileImageUrl;
    }

    public Users(Long id, String nickName, Role role) {
        this.id = id;
        this.nickName = nickName;
        this.role = role;
        this.detailStatus = false;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void setDetailStatus(boolean status) {
        this.detailStatus = status;
    }
}
