package uz.itransition.collectin.payload.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenResponse {

    private long id;

    private int roles;

    private String name;

    private String language;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("access_token")
    private String accessToken;
}
