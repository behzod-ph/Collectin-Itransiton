package uz.itransition.collectin.payload.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.collection.FieldValueListResponse;
import uz.itransition.collectin.payload.response.comment.CommentResponse;
import uz.itransition.collectin.payload.response.tag.TagResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

    private Long id;

    private String name;

    private List<TagResponse> tags;

    private FieldValueListResponse fields;

    private long likes;

    private List<CommentResponse> comments;

    private boolean liked;
}
