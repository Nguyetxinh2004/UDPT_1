package space.example.deliveryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.deliveryservice.dto.request.delivery.DeliveryReq;
import space.example.deliveryservice.dto.response.delivery.DeliveryResp;
import space.example.deliveryservice.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @Operation(
            summary = "Create a new delivery, for internal use only"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<DeliveryResp>> createDelivery(
            @RequestBody DeliveryReq deliveryReq
    ) {
        DeliveryResp deliveryResp = deliveryService.createDelivery(deliveryReq);

        ApiResponse<DeliveryResp> response = ApiResponse.<DeliveryResp>builder()
                .data(deliveryResp)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
