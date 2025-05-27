package space.example.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.example.commonservice.dto.response.ApiResponse;
import space.example.commonservice.dto.response.PagedResponse;
import space.example.foodservice.dto.request.FoodFilterReq;
import space.example.foodservice.dto.request.FoodUpdateReq;
import space.example.foodservice.entity.Food;
import space.example.foodservice.service.FoodService;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<Food>>> getFoods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @ParameterObject FoodFilterReq filter
    ) {
        PagedResponse<Food> foods = foodService.getFoods(page, size, filter);

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<Food>>builder()
                        .data(foods)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Food>> createFood(@RequestBody Food food) {
        Food createdFood = foodService.createFood(food);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Food>builder()
                        .data(createdFood)
                        .build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Food>> updateFood(
            @PathVariable String id,
            @RequestBody FoodUpdateReq foodDetails
    ) {
        Food updatedFood = foodService.updateFood(id, foodDetails);

        return ResponseEntity.ok(
                ApiResponse.<Food>builder()
                        .data(updatedFood)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable String id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Use for internal service to get food by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Food>> getFoodById(@PathVariable String id) {
        Food food = foodService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<Food>builder()
                        .data(food)
                        .build()
        );
    }

}
