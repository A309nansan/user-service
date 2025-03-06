package site.nansan.global.exception;

public class ExternalServerUnexpectedErrorException extends NANSANException {
    public ExternalServerUnexpectedErrorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
