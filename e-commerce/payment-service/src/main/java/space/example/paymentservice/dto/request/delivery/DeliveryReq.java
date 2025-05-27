package space.example.paymentservice.dto.request.delivery;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryReq {
    @Hidden
    private Long orderId;
    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    private String shipperName;
    private String shipperPhone;
}
