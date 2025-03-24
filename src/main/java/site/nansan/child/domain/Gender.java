package site.nansan.child.domain;

public enum Gender {
    BOY, GIRL;
    public static boolean isValidGender(String gender){
        try {
            Gender.valueOf(gender);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
