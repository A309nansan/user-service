package site.nansan.refresh.exception;

import site.nansan.global.exception.NANSANException;

public class AccessTokenExpiredException extends NANSANException {
    public AccessTokenExpiredException(){
        super(UserErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
