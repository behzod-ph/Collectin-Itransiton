package uz.itransition.collectin.payload.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.ElementType;
import uz.itransition.collectin.payload.response.Searchable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentSearchResponse implements Searchable {
    private long id;

    @JsonProperty(value = "name")
    private String itemName;

    @JsonProperty(value = "description")
    private String text;

    @JsonProperty(value = "item_id")
    private String itemId;

    private final ElementType type = ElementType.COMMENT;
}
