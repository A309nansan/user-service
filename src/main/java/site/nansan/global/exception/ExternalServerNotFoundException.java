package site.nansan.global.exception;

public class ExternalServerNotFoundException extends NANSANException {
    public ExternalServerNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
