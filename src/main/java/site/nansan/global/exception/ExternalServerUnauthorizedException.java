package site.nansan.global.exception;

public class ExternalServerUnauthorizedException extends NANSANException {
    public ExternalServerUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
