package ptithcm.tttn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class StatisticRequest {
    private int moth;
    private long total_price;
    public StatisticRequest(long total_price) {
        this.total_price = total_price;
    }
}
