package site.nansan.global.exception;

public class ExternalServerBadRequestException extends NANSANException {
    public ExternalServerBadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
