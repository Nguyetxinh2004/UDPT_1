package space.example.deliveryservice.dto.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.example.deliveryservice.entity.Order;
import space.example.deliveryservice.entity.OrderItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutItemResp {
    private Long id;
    private Long orderId;
    private String foodId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    public static PutItemResp of(OrderItem orderItem) {
        return PutItemResp.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .foodId(orderItem.getFoodId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getOrder().getTotalPrice()  )
                .build();
    }

}
