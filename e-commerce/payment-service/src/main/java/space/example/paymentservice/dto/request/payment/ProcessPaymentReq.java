package space.example.paymentservice.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessPaymentReq {
    private Long paymentId;
    private Long userId;
    private Long orderId;
    private Integer totalAmount;
}
