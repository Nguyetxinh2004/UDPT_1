package space.example.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.paymentservice.dto.request.delivery.DeliveryReq;
import space.example.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("process/{orderId}")
    public ResponseEntity<Void> processPayment(@PathVariable Long orderId, @RequestBody DeliveryReq deliveryReq) {
        paymentService.processPayment(orderId, deliveryReq);
        return ResponseEntity.noContent().build();
    }

}
