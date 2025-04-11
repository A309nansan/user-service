package site.nansan.user.dto;

import lombok.Builder;
import lombok.Getter;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.SocialPlatform;
import site.nansan.user.domain.Users;

@Getter
@Builder
public class UserDto {

    private Long id;

    private String platformId;

    private SocialPlatform socialPlatform;

    private String email;

    private String nickName;

    private Role role;

    private Boolean detailStatus;

    private String hashId;

    private String profileImageUrl;

    public static UserDto from(
            Users entity
    ) {

        return UserDto.builder()
                .id(entity.getId())
                .platformId(entity.getPlatformId())
                .socialPlatform(entity.getSocialPlatform())
                .email(entity.getEmail())
                .nickName(entity.getNickName())
                .role(entity.getRole())
                .detailStatus(entity.getDetailStatus())
                .hashId(entity.getHashId())
                .profileImageUrl(entity.getProfileImageUrl())
                .build();
    }
}
