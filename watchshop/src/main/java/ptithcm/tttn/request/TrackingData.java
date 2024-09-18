package ptithcm.tttn.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrackingData {
    private String trackingNumber;
    private String carrierCode;
    private String status;
    private String updatedAt;
}
