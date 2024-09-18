package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ValueResponse {
    private Integer value;
    private int code;
    private HttpStatus status;
    private String message;

    public ValueResponse() {
        
    }
}
