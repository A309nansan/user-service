package site.nansan.refresh.exception;

import site.nansan.global.exception.NANSANException;

public class RefreshTokenNotExistException extends NANSANException {
    public RefreshTokenNotExistException(){
        super(UserErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
