package space.example.deliveryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.deliveryservice.dto.request.order.CreateOrderReq;
import space.example.deliveryservice.dto.request.order.PutItemReq;
import space.example.deliveryservice.dto.response.order.OrderResp;
import space.example.deliveryservice.dto.response.order.PutItemResp;
import space.example.deliveryservice.entity.OrderStatus;
import space.example.deliveryservice.service.OrderService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResp> createOrder(@RequestBody CreateOrderReq createOrderReq) {
        OrderResp createOrderResp = orderService.createOrder(createOrderReq);
        return ResponseEntity.ok(createOrderResp);
    }

    @PutMapping("/{orderId}/item")
    public ResponseEntity<ApiResponse<PutItemResp>> putItem(
            @PathVariable Long orderId,
            @RequestBody PutItemReq putItemReq) {

        PutItemResp putItemResp = orderService.putItem(orderId, putItemReq);

        return ResponseEntity.ok(
                ApiResponse.<PutItemResp>builder()
                        .data(putItemResp)
                        .build()
        );
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get order by orderId and userId, for internal use only"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<OrderResp>> getOrderById(
            @RequestParam Long orderId,
            @RequestParam Long userId
    ) {
        OrderResp orderResp = orderService.getOrderByOrderIdAndUserId(orderId, userId);
        return ResponseEntity.ok(ApiResponse.<OrderResp>builder().data(orderResp).build());
    }
}
