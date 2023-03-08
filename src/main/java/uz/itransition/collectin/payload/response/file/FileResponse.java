package uz.itransition.collectin.payload.response.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.itransition.collectin.payload.response.Response;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse implements Response {
    List<String> path;
}
