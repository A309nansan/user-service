package site.nansan.user.domain;

public enum SocialPlatform {
    GOOGLE, KAKAO, NAVER;
    public static boolean isValidSocialPlatform(String platform) {
        try {
            SocialPlatform.valueOf(platform);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
