package site.nansan.child.dto;

import lombok.Getter;
import lombok.Setter;
import site.nansan.child.domain.Gender;
import site.nansan.child.domain.Grade;

import java.time.LocalDate;

@Getter
@Setter
public class ChildRequest {
    private String name;
    private String profileImageUrl;
    private LocalDate birthDate;
    private Grade grade;
    private Gender gender;
}
