package site.nansan.global.exception;

public class ExternalInternalServerError extends NANSANException {
    public ExternalInternalServerError(ErrorCode errorCode) {
        super(errorCode);
    }
}
