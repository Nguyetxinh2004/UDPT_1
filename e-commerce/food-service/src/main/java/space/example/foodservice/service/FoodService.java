package space.example.foodservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.example.commonservice.common.Constant;
import space.example.commonservice.dto.response.PagedResponse;
import space.example.commonservice.exception.AlreadyExistedException;
import space.example.commonservice.exception.NotFoundException;
import space.example.foodservice.dto.request.FoodFilterReq;
import space.example.foodservice.dto.request.FoodUpdateReq;
import space.example.foodservice.entity.Food;
import space.example.foodservice.repository.FoodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional
    public PagedResponse<Food> getFoods(int page, int size, FoodFilterReq foodFilterReq) {
        Criteria criteria = new Criteria();

        List<Criteria> filters = new ArrayList<>();

        if (StringUtils.isNotBlank(foodFilterReq.getName())) {
            filters.add(Criteria.where("name").regex(foodFilterReq.getName(), "i"));
        }

        if (StringUtils.isNotBlank(foodFilterReq.getCategory())) {
            filters.add(Criteria.where("category").regex(foodFilterReq.getCategory(), "i"));
        }


        if (foodFilterReq.getPriceFrom() != null || foodFilterReq.getPriceTo() != null) {
            Criteria priceCriteria = Criteria.where("price");
            if (foodFilterReq.getPriceFrom() != null) priceCriteria = priceCriteria.gte(foodFilterReq.getPriceFrom());
            if (foodFilterReq.getPriceTo() != null) priceCriteria = priceCriteria.lte(foodFilterReq.getPriceTo());
            filters.add(priceCriteria);
        }

        if (!filters.isEmpty()) {
            criteria.andOperator(filters.toArray(new Criteria[0]));
        }

        Query query = new Query(criteria).with(PageRequest.of(page, size));
        List<Food> foods = mongoTemplate.find(query, Food.class);
        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Food.class);

        return PagedResponse.<Food>builder()
                .elements(foods)
                .totalElements(total)
                .totalPages((int) Math.ceil((double) total / size))
                .hasNext((long) (page + 1) * size < total)
                .hasPrevious(page > 0)
                .build();
    }

    @Transactional
    public Food createFood(Food food) {
        boolean isExisted = foodRepository.existsById(food.getId());

        if (isExisted) {
            throw new AlreadyExistedException(Constant.ErrorCode.FOOD_EXISTS + food.getId());
        }

        return foodRepository.save(food);
    }

    @Transactional
    public Food updateFood(String id, FoodUpdateReq foodDetails) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND + id));

        food.setName(foodDetails.getName());
        food.setDescription(foodDetails.getDescription());
        food.setPrice(foodDetails.getPrice());
        food.setCategory(foodDetails.getCategory());
        food.setAvailable(foodDetails.isAvailable());
        food.setTags(foodDetails.getTags());

        return foodRepository.save(food);
    }

    @Transactional
    public void deleteFood(String id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND + id));

        foodRepository.delete(food);
    }

    @Transactional(readOnly = true)
    public Food getById(String id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND + id));
    }
}
