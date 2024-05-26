package core.repository;

import core.domain.Cat;
import core.domain.Food;
import core.repository.custom.FoodCustomRepository;
import lombok.experimental.PackagePrivate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FoodRepository extends Repository<Food, Long>, FoodCustomRepository {
    List<Food> findAllByExpirationDateAfter(Date date);

    @Query("select distinct f from Food f")
    @EntityGraph(value = "foodWithCatFoodAndCat", type = EntityGraph.EntityGraphType.LOAD)
    List<Food> findAllWithCatFood();

    @Query("select distinct f from Food f where f.catFoodList.size > :count")
    @EntityGraph(value = "foodWithCatFoodAndCat", type = EntityGraph.EntityGraphType.LOAD)
    List<Food> findAllFoodEatenByMoreThan(@Param("count") Integer count);
}
