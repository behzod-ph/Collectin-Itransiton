package uz.itransition.collectin.payload.request.collection;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.service.marker.Modifiable;

@Getter
@Setter
public class FieldValueUpdateRequest implements Modifiable {

    private String value;

    @JsonProperty("field_id")
    private long fieldId;
}
