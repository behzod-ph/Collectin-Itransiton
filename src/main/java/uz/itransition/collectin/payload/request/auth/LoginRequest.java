package uz.itransition.collectin.payload.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.request.Request;
import uz.itransition.collectin.service.marker.Creationable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Request, Creationable {
    @Email
    private String email;
    @Size(min = 4, max = 18)
    private String password;
}
