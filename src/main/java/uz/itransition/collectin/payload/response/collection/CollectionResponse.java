package uz.itransition.collectin.payload.response.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.request.Request;
import uz.itransition.collectin.payload.response.user.CollectionUserResponse;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionResponse implements Request {

    private long id;

    private String name;

    private TopicResponse topic;

    @JsonProperty("image_url")
    private String imageUrl;

    private String description;

    @JsonProperty("creation_date")
    private Date creationDate;

    @JsonProperty("collection_author")
    private CollectionUserResponse userResponse;

    private FieldResponse[] fields;
}
