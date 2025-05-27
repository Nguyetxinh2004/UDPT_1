package space.example.paymentservice.dto.response;

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
public class DeliveryDto {
    private Long deliveryId;
    private Long orderId;
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
    private String shipperName;
    private String shipperPhone;

}
