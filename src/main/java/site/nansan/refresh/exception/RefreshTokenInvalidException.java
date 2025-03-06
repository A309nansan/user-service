package site.nansan.refresh.exception;

import site.nansan.global.exception.NANSANException;

public class RefreshTokenInvalidException extends NANSANException {
    public RefreshTokenInvalidException() {
        super(UserErrorCode.INVALID_REFRESH_TOKEN);
    }
}
