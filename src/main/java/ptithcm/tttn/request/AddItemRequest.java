package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class AddItemRequest {
    private String product_name;
    private Integer price;
}
