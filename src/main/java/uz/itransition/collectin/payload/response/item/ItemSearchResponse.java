package uz.itransition.collectin.payload.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.ElementType;
import uz.itransition.collectin.payload.response.Searchable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSearchResponse implements Searchable {
    private long id;

    private String name;

//    private final String type="item";

    private final ElementType type = ElementType.ITEM;
}
