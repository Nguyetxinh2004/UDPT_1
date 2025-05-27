package space.example.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.example.commonservice.common.Constant;
import space.example.commonservice.exception.NotFoundException;
import space.example.commonservice.util.AuthenticationUtil;
import space.example.paymentservice.client.DeliveryClient;
import space.example.paymentservice.dto.request.delivery.DeliveryReq;
import space.example.paymentservice.dto.response.order.OrderResp;
import space.example.paymentservice.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PaymentService")
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final DeliveryClient deliveryClient;

    @Transactional
    public void processPayment(Long orderId, DeliveryReq deliveryReq) {
        Long currentUserId = AuthenticationUtil.getCurrentUserId();
        OrderResp order = deliveryClient.getOrderById(orderId, currentUserId).getData();

        log.info("Processing payment order: {}", order);

        if (order == null) {
            throw new NotFoundException(Constant.ErrorCode.NOT_FOUND, orderId, currentUserId);
        }

        deliveryReq.setOrderId(orderId);

        deliveryClient.createDelivery(deliveryReq);

    }
}
