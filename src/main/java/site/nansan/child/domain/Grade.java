package site.nansan.child.domain;

public enum Grade {
    PRESCHOOL,
    ELEMENTARY_1,
    ELEMENTARY_2,
    ELEMENTARY_3,
    ELEMENTARY_4,
    ELEMENTARY_5,
    ELEMENTARY_6,
    MIDDLE_1,
    MIDDLE_2,
    MIDDLE_3,
    HIGH_1,
    HIGH_2,
    HIGH_3;
    public static boolean isValidGrade(String grade){
        try {
            Grade.valueOf(grade);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
