package site.nansan.child.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "child")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Setter

public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "profile_image_url", length = 255, nullable = false)
    private String profileImageUrl;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Builder
    public Child(String name, String profileImageUrl, LocalDate birthDate, Grade grade, Gender gender) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.birthDate = birthDate;
        this.grade = grade;
        this.gender = gender;
    }
}