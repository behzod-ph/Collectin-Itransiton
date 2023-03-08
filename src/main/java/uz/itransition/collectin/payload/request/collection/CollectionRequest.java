package uz.itransition.collectin.payload.request.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.request.Request;
import uz.itransition.collectin.payload.request.field.FieldRequest;
import uz.itransition.collectin.service.marker.Creationable;
import uz.itransition.collectin.service.marker.Modifiable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionRequest implements Request, Creationable, Modifiable {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("topic_id")
    private long topicId;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("custom_fields")
    private FieldRequest[] customFields;
}
