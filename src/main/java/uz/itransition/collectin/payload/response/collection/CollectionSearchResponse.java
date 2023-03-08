package uz.itransition.collectin.payload.response.collection;


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
public class CollectionSearchResponse implements Searchable {
    private long id;

    private String name;

    private String description;

    private final ElementType type = ElementType.COLLECTION;
}
