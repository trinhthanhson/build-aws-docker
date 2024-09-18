package ptithcm.tttn.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponse {

    private String token;
    private boolean status;

}
