package uz.itransition.collectin.exception;
public class JwtValidationException extends RuntimeException{
    public JwtValidationException(String message) {
        super(message);
    }
}
