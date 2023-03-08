package uz.itransition.collectin.payload.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.Response;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTagResponse implements Response {

    private long itemId;

    private String fieldKey;

    private String fieldValue;
}
