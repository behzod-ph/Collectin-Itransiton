package uz.itransition.collectin.exception;

public class DataNotFoundException extends ApiException{

    private static final String DEFAULT_MESSAGE_ENG = "%s not found with %s";

    public DataNotFoundException(String message) {
        super(message);
    }

    public static DataNotFoundException of(String resource, String parameter)
    {
        return new DataNotFoundException(String.format(DEFAULT_MESSAGE_ENG,resource,parameter));
    }
}
