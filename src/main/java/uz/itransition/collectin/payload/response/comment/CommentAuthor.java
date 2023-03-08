package uz.itransition.collectin.payload.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.DTO;

@Getter
@Setter
public class CommentAuthor implements DTO {

    private long id;

    private String name;

    @JsonProperty("image_url")
    private String imageUrl;
}
