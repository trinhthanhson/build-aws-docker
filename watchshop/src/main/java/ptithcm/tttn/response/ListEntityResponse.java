package ptithcm.tttn.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Getter
@Setter
public class ListEntityResponse<T> {
    private List<T> data;
    private HttpStatus status;
    private String message;
    private int code;
}
