package site.nansan.child.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "childs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Childs {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디

    @Column(nullable = false, length = 30)
    private String name; // 이름

    @Column(nullable = false, length = 255)
    private String profileImageUrl; // 프로필 사진

    @Column(nullable = false)
    private LocalDate birthDate; // 생년월일

    @Column(nullable = false)
    private int grade; // 학년

    @Column(nullable = false)
    private int gender; // 성별 (남: 0, 여: 1)
}
