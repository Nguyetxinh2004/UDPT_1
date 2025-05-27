package space.example.deliveryservice.dto.request.delivery;

import jakarta.validation.constraints.NotNull;
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
    private Long orderId;
    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    private String shipperName;
    private String shipperPhone;
}
