package site.nansan.refresh.exception;

import site.nansan.global.exception.NANSANException;

public class AccessTokenInvalidException extends NANSANException {
    public AccessTokenInvalidException(){
        super(UserErrorCode.INVALID_ACCESS_TOKEN);
    }
}
