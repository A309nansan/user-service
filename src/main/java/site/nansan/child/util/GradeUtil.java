package site.nansan.child.util;


import site.nansan.child.domain.Grade;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

public class GradeUtil {

    private static final List<Grade> gradeOrder = Arrays.asList(
            Grade.PRESCHOOL,

            Grade.ELEMENTARY_1_1, Grade.ELEMENTARY_1_2,
            Grade.ELEMENTARY_2_1, Grade.ELEMENTARY_2_2,
            Grade.ELEMENTARY_3_1, Grade.ELEMENTARY_3_2,
            Grade.ELEMENTARY_4_1, Grade.ELEMENTARY_4_2,
            Grade.ELEMENTARY_5_1, Grade.ELEMENTARY_5_2,
            Grade.ELEMENTARY_6_1, Grade.ELEMENTARY_6_2,

            Grade.MIDDLE_1_1, Grade.MIDDLE_1_2,
            Grade.MIDDLE_2_1, Grade.MIDDLE_2_2,
            Grade.MIDDLE_3_1, Grade.MIDDLE_3_2,

            Grade.HIGH_1_1, Grade.HIGH_1_2,
            Grade.HIGH_2_1, Grade.HIGH_2_2,
            Grade.HIGH_3_1, Grade.HIGH_3_2,

            Grade.ADULT
    );

    public static Grade getNextGrade(Grade currentGrade, LocalDate birthDate, LocalDate today) {
        if (currentGrade == null || birthDate == null || today == null) return null;

        if (currentGrade == Grade.ADULT) return Grade.ADULT;

        int age = Period.between(birthDate, today).getYears();
        if (currentGrade == Grade.PRESCHOOL && age == 7 && today.getMonth() == Month.MARCH) {
            return Grade.ELEMENTARY_1_1;
        }

        Month currentMonth = today.getMonth();
        boolean isMarch = currentMonth == Month.MARCH;
        boolean isSeptember = currentMonth == Month.SEPTEMBER;

        if (isMarch || isSeptember) {
            int idx = gradeOrder.indexOf(currentGrade);
            if (idx != -1 && idx + 1 < gradeOrder.size()) {
                return gradeOrder.get(idx + 1);
            }
        }

        return currentGrade;
    }
}
