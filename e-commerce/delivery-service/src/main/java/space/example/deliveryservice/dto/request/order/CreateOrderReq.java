package space.example.deliveryservice.dto.request.order;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReq {
    @Hidden
    private String status = "PENDING";
    private BigDecimal totalPrice;
}
