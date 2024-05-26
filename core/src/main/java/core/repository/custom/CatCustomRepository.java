package core.repository.custom;

import core.domain.Cat;
import core.domain.Food;
import core.domain.Purchase;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CatCustomRepository {
    List<Cat> filterCatsThatEatCertainFood(Food food);

    void addPurchaseToCat(Purchase purchase, Long catId);

    public List<Cat> findAllWithPurchases();
}
