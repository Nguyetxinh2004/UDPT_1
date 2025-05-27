package space.example.paymentservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import space.example.commonservice.config.OpenFeignConfig;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.paymentservice.dto.request.delivery.DeliveryReq;
import space.example.paymentservice.dto.response.DeliveryDto;
import space.example.paymentservice.dto.response.order.OrderResp;

@FeignClient(name = "delivery-service", configuration = OpenFeignConfig.class)
public interface DeliveryClient {
    @PostMapping("/api/delivery")
    ApiResponse<DeliveryDto> createDelivery(@RequestBody DeliveryReq deliveryReq);

    @GetMapping("/api/order")
    ApiResponse<OrderResp> getOrderById(
            @RequestParam Long orderId,
            @RequestParam Long userId
    );
}
