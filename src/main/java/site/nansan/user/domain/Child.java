package site.nansan.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
// todo : 테이블명은 대문자 넣지 않아여
@Table(name = "Child")
@Getter
/* todo : Setter은 어노테이션으로 추가하는 것은 좋은 패턴이 아니에요.
    setter을 추가하고 싶으면 필드명 위에 추가하면 돼요.
    하지만 로직상 테이블의 값을 바꿔야 할 경우가 당연히 있을텐데 그땐
    set필드명 대신에 명시적인 이름을 넣어도 되고 ( 가장 권장됨 )
    약간 뻔하고, 해당 테이블의 정보가 탈취시 치명적인 데이터가 아니라면
    그냥 setter 어노테이션을 통해 바꿔도 되요.
 */
@Setter
/*
todo : Child Service의 24번째 줄에 child Entity가 잘못 설계되었을 확율이 높다고 했는데
이 부분에 보면 생성자가 없음. 생성자가 없으니 setter로 값을 주입했음. 가능하지만 권장되진 않음.
생성자보다도 builder가 휴먼 에러를 방지하기 좋음. builder을 추가하려면 어노테이션에
@builder을 추가하면 되는데, Builder 어노테이션을 추가하려면 @AllArgsConstructor가 필요함.
근데 전체 생성자를 추가하면 id 값등 휴먼 에러가 또 발생할 수도 있으니, 전체 생성자를 protected로
넣어서 외부에서 호출되지 않게 설계하면 좋음.

결론 : 전체 생성자를 만들되, 외부에서 생성하지 못하게 protected로 만들고, builder을 사용한다.
그러면 Child.builder.name("이름")....build()로 생성할 수가 있다.

 */
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
    // todo : 이런건 enum 값으로 넣어야 좋아요. enum 으로 male, female 해서
    // private Gender gender; 이런 식으로 선언하면 나중에 생성할때
    // Gender.MALE 이런식으로 선언하면 명시적입니다. 다른 사람이 코드 볼때
    // 남자가 0인지 확인할 필요가 없죠
    private Integer gender; // 0: 남, 1: 여
}