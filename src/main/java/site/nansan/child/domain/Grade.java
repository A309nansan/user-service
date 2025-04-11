package site.nansan.child.domain;

public enum Grade {
    PRESCHOOL,

    ELEMENTARY_1_1, ELEMENTARY_1_2,
    ELEMENTARY_2_1, ELEMENTARY_2_2,
    ELEMENTARY_3_1, ELEMENTARY_3_2,
    ELEMENTARY_4_1, ELEMENTARY_4_2,
    ELEMENTARY_5_1, ELEMENTARY_5_2,
    ELEMENTARY_6_1, ELEMENTARY_6_2,

    MIDDLE_1_1, MIDDLE_1_2,
    MIDDLE_2_1, MIDDLE_2_2,
    MIDDLE_3_1, MIDDLE_3_2,

    HIGH_1_1, HIGH_1_2,
    HIGH_2_1, HIGH_2_2,
    HIGH_3_1, HIGH_3_2,

    ADULT;

    public static boolean isValidGrade(String grade){
        try {
            Grade.valueOf(grade);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
