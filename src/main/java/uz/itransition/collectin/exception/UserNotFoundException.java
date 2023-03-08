package uz.itransition.collectin.exception;


public class UserNotFoundException extends ApiException {

    private static final String DEFAULT_MESSAGE_ENG ="User not found with %s";


    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException of(String parameter){
        return new UserNotFoundException(String.format(DEFAULT_MESSAGE_ENG,parameter));
    }
}
