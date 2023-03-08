package uz.itransition.collectin.payload.response.tag;

import lombok.Getter;
import lombok.Setter;
import uz.itransition.collectin.payload.response.Response;

@Getter
@Setter
public class TagResponse implements Response {

    private long id;

    private String name;
}
