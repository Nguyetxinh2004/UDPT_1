package space.example.deliveryservice.dto.response.delivery;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.example.deliveryservice.entity.Delivery;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryResp {
    private Long deliveryId;
    private Long orderId;
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
    private String shipperName;
    private String shipperPhone;

    public static DeliveryResp of(Delivery delivery) {
        return DeliveryResp.builder()
                .deliveryId(delivery.getId())
                .orderId(delivery.getOrderId())
                .deliveryTime(delivery.getDeliveryTime())
                .deliveryAddress(delivery.getDeliveryAddress())
                .shipperName(delivery.getShipperName())
                .shipperPhone(delivery.getShipperPhone())
                .build();
    }
}
