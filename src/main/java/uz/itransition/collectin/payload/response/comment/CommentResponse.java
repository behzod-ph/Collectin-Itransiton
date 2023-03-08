package uz.itransition.collectin.payload.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CommentResponse {
    private long id;

    @JsonProperty("item_id")
    private long itemId;

    private String text;

    @JsonProperty("creation_date")
    private Date creationDate;

    @JsonProperty("user")
    private CommentAuthor commentAuthor;
}
