package uz.itransition.collectin.payload.request.collection;

import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.service.marker.Modifiable;

import java.util.List;

@Getter
@Setter
public class ItemUpdateRequest implements Modifiable {
    private String name;
    private List<Long> tags;
    private List<FieldValueUpdateRequest> fieldValues;
}
