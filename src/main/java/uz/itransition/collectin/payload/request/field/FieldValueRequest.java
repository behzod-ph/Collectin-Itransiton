package uz.itransition.collectin.payload.request.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.DTO;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldValueRequest implements DTO, Creationable, Modifiable {
    private String value;
    @JsonProperty("item_id")
    private long itemId;
    @JsonProperty("field_id")
    private long fieldId;
}
