package uz.itransition.collectin.payload.request.user;

import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.request.Request;
import uz.itransition.collectin.service.marker.Creationable;

@Getter
@Setter
public class UserRegistrationRequest implements Request, Creationable {
    private String name;
    private String email;
    private String password;
}
