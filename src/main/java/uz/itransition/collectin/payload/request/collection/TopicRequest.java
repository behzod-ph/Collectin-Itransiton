package uz.itransition.collectin.payload.request.collection;

import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.request.Request;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TopicRequest implements Request, Creationable, Modifiable {

    @NotBlank
    private String name;

}
