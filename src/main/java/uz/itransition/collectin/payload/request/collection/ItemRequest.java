package uz.itransition.collectin.payload.request.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.request.Request;
import uz.itransition.collectin.payload.request.field.FieldValueRequest;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;

import java.util.List;

@Getter
@Setter
public class ItemRequest implements Request,  Creationable, Modifiable {

    @JsonProperty("collection_id")
    private long collectionId;

    @JsonProperty("item_name")
    private String name;

    @JsonProperty("tag_ids")
    private List<Long> tagIdList;

    @JsonProperty("field_values")
    private List<FieldValueRequest> fieldValues;
}
