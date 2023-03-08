package uz.itransition.collectin.payload.response.collection;

import lombok.*;
import uz.itransition.collectin.payload.response.Response;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicResponse implements Response {

    private Long id;

    private String name;
}
