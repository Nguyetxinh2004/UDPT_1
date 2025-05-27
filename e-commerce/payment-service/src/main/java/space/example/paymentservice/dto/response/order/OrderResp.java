package space.example.paymentservice.dto.response.order;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderResp {
    private Long orderId;
    @Hidden
    private String status;
    private Integer totalPrice;


}
