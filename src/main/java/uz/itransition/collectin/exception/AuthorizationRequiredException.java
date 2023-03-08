package uz.itransition.collectin.exception;

public class AuthorizationRequiredException extends ApiException{
    private static final String DEFAULT_MESSAGE_ENG = "Authorization required to do specific action";


    public AuthorizationRequiredException(String message) {
        super(message);
    }

    public AuthorizationRequiredException() {
        super(DEFAULT_MESSAGE_ENG);
    }
}
