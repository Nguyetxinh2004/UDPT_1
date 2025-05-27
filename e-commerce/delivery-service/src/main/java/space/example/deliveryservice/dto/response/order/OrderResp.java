package space.example.deliveryservice.dto.response.order;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.example.deliveryservice.entity.Order;
import space.example.deliveryservice.entity.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResp {
    private Long orderId;
    @Hidden
    private OrderStatus status;
    private Integer totalPrice;

    public static OrderResp of(Order order) {
        return OrderResp.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
