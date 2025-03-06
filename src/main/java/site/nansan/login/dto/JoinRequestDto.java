package site.nansan.login.dto;

import lombok.Data;

@Data
public class JoinRequestDto {
    private String socialPlatform;
    private String email;
    private String platformId;
    private String nickName;
}
