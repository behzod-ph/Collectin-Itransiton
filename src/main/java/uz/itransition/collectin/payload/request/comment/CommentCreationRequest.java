package uz.itransition.collectin.payload.request.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.service.marker.Creationable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentCreationRequest implements Creationable {

    @NotNull
    @JsonProperty("item_id")
    private Long itemId;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank
    private String text;
}
