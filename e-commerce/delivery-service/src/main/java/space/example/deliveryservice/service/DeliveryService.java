package space.example.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.example.commonservice.common.Constant;
import space.example.commonservice.exception.AlreadyExistedException;
import space.example.deliveryservice.dto.request.delivery.DeliveryReq;
import space.example.deliveryservice.dto.response.delivery.DeliveryResp;
import space.example.deliveryservice.entity.Delivery;
import space.example.deliveryservice.entity.OrderStatus;
import space.example.deliveryservice.repository.DeliveryRepository;
import space.example.deliveryservice.repository.OrderItemRepository;
import space.example.deliveryservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderService orderService;

    @Transactional
    public DeliveryResp createDelivery(DeliveryReq deliveryReq) {
        boolean isExistingDelivery = deliveryRepository.existsByOrderId((deliveryReq.getOrderId()));

        if (isExistingDelivery) {
            throw new AlreadyExistedException(Constant.ErrorCode.DELIVERY_ALREADY_EXISTS, deliveryReq.getOrderId());
        }

        Delivery deliveryEntity = Delivery.builder()
                .deliveryAddress(deliveryReq.getDeliveryAddress())
                .deliveryTime(deliveryReq.getDeliveryTime())
                .shipperName(deliveryReq.getShipperName())
                .shipperPhone(deliveryReq.getShipperPhone())
                .orderId(deliveryReq.getOrderId())
                .build();

        Delivery savedDelivery = deliveryRepository.save(deliveryEntity);

        orderService.updateOrderStatus(deliveryEntity.getOrderId(), OrderStatus.SHIPPED);

        return DeliveryResp.of(savedDelivery);
    }


}
