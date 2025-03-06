package site.nansan.refresh.exception;

import site.nansan.global.exception.NANSANException;

public class LoginJsonParsingException extends NANSANException {
    public LoginJsonParsingException(){
        super(UserErrorCode.EXPIRED_ACCESS_TOKEN);
    }
}
