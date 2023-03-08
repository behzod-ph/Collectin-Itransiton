package uz.itransition.collectin.payload.request;

import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.DTO;
import uz.itransition.collectin.service.marker.Creationable;

import java.util.List;

@Getter
@Setter
public class UserStatusRequest implements DTO, Creationable {
    private String action;
    private List<Long> content;
}
