package site.nansan.global.exception;

public class ExternalServerForbiddenException extends NANSANException {
    public ExternalServerForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
