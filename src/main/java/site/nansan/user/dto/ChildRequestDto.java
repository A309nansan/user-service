package site.nansan.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
// todo : 우리는 Dto라는 이름 안쓰고 Response, Request로 통합 시키기로 했습니다.
public class ChildRequestDto {
    private String name;
    private String profileImageUrl;
    private LocalDate birthDate;
    private Integer grade;
    private Integer gender;
}
