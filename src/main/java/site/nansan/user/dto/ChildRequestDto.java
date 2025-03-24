package site.nansan.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChildRequestDto {
    private String name;
    private String profileImageUrl;
    private LocalDate birthDate;
    private Integer grade;
    private Integer gender;
}
