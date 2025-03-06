package site.nansan.refresh.exception;

import site.nansan.global.exception.NANSANException;

public class RefreshTokenExpiredException extends NANSANException {
    public RefreshTokenExpiredException(){
        super(UserErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
