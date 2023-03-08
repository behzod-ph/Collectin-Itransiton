package uz.itransition.collectin.payload.response.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.Response;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldValueResponse implements Response {

    private String value;

    @JsonProperty("item_id")
    private long itemId;

    @JsonProperty("field_id")
    private long fieldId;
}