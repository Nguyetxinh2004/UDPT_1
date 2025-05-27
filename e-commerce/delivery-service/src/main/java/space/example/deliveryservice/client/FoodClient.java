package space.example.deliveryservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.example.commonservice.config.OpenFeignConfig;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.deliveryservice.dto.response.food.FoodDto;

@FeignClient(name="food-service", configuration = OpenFeignConfig.class)
public interface FoodClient {
    @GetMapping("/api/food/{id}")
    ResponseEntity<ApiResponse<FoodDto>> getFoodById(@PathVariable("id") String foodId);
}
