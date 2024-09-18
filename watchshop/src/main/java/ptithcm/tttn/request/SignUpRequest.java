package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String role_name;
    private String otp;
}
