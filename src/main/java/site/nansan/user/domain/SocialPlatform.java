package site.nansan.user.domain;

public enum SocialPlatform {
    GOOGLE, KAKAO, NAVER, FACE_BOOK;
    public static boolean isValidSocialPlatform(String platform) {
        try {
            SocialPlatform.valueOf(platform);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
