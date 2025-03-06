package site.nansan.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NANSANException extends RuntimeException{
    private ErrorCode errorCode;
}
