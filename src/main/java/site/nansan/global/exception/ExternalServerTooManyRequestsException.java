package site.nansan.global.exception;

public class ExternalServerTooManyRequestsException extends NANSANException {
    public ExternalServerTooManyRequestsException(ErrorCode errorCode) {
        super(errorCode);
    }
}

