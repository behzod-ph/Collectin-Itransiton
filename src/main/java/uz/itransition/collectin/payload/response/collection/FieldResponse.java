package uz.itransition.collectin.payload.response.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.Response;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldResponse implements Response {

    private long id;

    private int type;

    private String name;
}
