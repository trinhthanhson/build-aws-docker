package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponse {
    private String status;
    private String orderInfo;
    private String totalPrice;
    private String paymentTime;
    private String transactionId;
}
