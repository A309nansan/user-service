package site.nansan.global.exception;

public class InvalidDataException extends NANSANException {
    public InvalidDataException(ErrorCode errorCode) {
        super(errorCode);
    }
}
