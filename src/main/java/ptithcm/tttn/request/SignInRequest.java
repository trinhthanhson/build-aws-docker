package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignInRequest {
    private String username;
    private String password;
}
