package core.repository.custom;

import core.domain.Food;

import java.util.List;

public interface FoodCustomRepository {
    List<Food> findAllFoodNotExpired();


    List<Food> findAllFoodFromProducer(String producer);
}
