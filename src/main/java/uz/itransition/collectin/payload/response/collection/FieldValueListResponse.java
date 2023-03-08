package uz.itransition.collectin.payload.response.collection;

import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.DTO;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FieldValueListResponse implements DTO, Creationable, Modifiable {

    private List<FieldResponse> types;

    private Map<Long, List<FieldValueResponse>> values;
}
