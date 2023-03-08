package uz.itransition.collectin.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
