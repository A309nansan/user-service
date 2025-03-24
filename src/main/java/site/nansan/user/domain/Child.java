package site.nansan.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Child")
@Getter
@Setter
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "gender", nullable = false)
    private Integer gender; // 0: 남, 1: 여
}