package uz.itransition.collectin.payload.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRoleUpdateRequest {
    @JsonProperty("set_as_admin")
    private boolean setAsAdmin;

    private List<Long> content;
}
