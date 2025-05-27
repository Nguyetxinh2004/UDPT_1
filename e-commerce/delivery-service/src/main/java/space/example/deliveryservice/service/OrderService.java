package space.example.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.example.commonservice.common.Constant;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.commonservice.exception.NotFoundException;
import space.example.commonservice.exception.OrderUpdateNotAllowedException;
import space.example.commonservice.util.AuthenticationUtil;
import space.example.deliveryservice.client.FoodClient;
import space.example.deliveryservice.dto.request.order.CreateOrderReq;
import space.example.deliveryservice.dto.request.order.PutItemReq;
import space.example.deliveryservice.dto.response.food.FoodDto;
import space.example.deliveryservice.dto.response.order.OrderResp;
import space.example.deliveryservice.dto.response.order.PutItemResp;
import space.example.deliveryservice.entity.Order;
import space.example.deliveryservice.entity.OrderItem;
import space.example.deliveryservice.entity.OrderStatus;
import space.example.deliveryservice.repository.OrderItemRepository;
import space.example.deliveryservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "OrderService")
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final FoodClient foodClient;

    @Transactional
    public OrderResp createOrder(CreateOrderReq createOrderReq) {
        Long currentUserId = AuthenticationUtil.getCurrentUserId();

        log.info("Food test {}", foodClient.getFoodById("string").getBody().getData());

        Order order = modelMapper.map(createOrderReq, Order.class);
        order.setUserId(currentUserId);

        return modelMapper.map(orderRepository.save(order), OrderResp.class);
    }

    @Transactional
    public PutItemResp putItem(Long orderId, PutItemReq putItemReq) {
        FoodDto foodDto = getFoodById(putItemReq.getFoodId());

        log.info("Food details: {}", foodDto);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, orderId));

        if (isOrderShipped(order)) {
            throw new OrderUpdateNotAllowedException(Constant.ErrorCode.ORDER_PUT_ITEM_FAILED, orderId);
        }

        OrderItem existingItem = orderItemRepository.findByOrderIdAndFoodId(orderId, putItemReq.getFoodId());

        int foodPrice = foodDto.getPrice();
        int quantityToAdd = putItemReq.getQuantity();

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantityToAdd;
            existingItem.setQuantity(newQuantity);
            existingItem.setTotalPrice(foodPrice * newQuantity);

            order.setTotalPrice(order.getTotalPrice() + foodPrice * quantityToAdd);
            orderRepository.save(order);

            return PutItemResp.of(orderItemRepository.save(existingItem));
        } else {
            OrderItem newItem = OrderItem.builder()
                    .order(order)
                    .unitPrice(foodPrice)
                    .foodId(putItemReq.getFoodId())
                    .quantity(quantityToAdd)
                    .totalPrice(foodPrice * quantityToAdd)
                    .build();

            order.setTotalPrice(order.getTotalPrice() + foodPrice * quantityToAdd);
            orderRepository.save(order);

            return PutItemResp.of(orderItemRepository.save(newItem));
        }
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, orderId));

        order.setStatus(orderStatus);
        orderRepository.save(order);
    }

    @Transactional
    public OrderResp getOrderByOrderIdAndUserId(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.ORDER_NOT_FOUND, orderId, userId));

        return OrderResp.of(order);
    }

    private boolean isOrderShipped(Order order) {
        return order.getStatus().equals(OrderStatus.SHIPPED);
    }


    private FoodDto getFoodById(String foodId) {
        ApiResponse<FoodDto> food = foodClient.getFoodById(foodId).getBody();

        if (food == null || food.getData() == null) {
            throw new NotFoundException(Constant.ErrorCode.NOT_FOUND, foodId);
        }

        return food.getData();
    }

}
