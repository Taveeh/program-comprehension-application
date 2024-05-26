package core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.domain.Food;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("/dbtest/db-data.xml")
public class FoodServiceTest {
    @Autowired
    private FoodService foodService;

    @Test
    public void getFoodFromRepository() {
        assertEquals("there should be 2 food", 2, foodService.getFoodFromRepository().size());
    }

    @Test
    public void addFood() {
        foodService.addFood("whiskas", "da", new Date());
        assertEquals("there should be 3 food", 3, foodService.getFoodFromRepository().size());
    }

    @Test
    public void deleteFood() {
        foodService.deleteFood(1L);
        assertEquals("there should be 1 food", 1, foodService.getFoodFromRepository().size());
    }

    @Test
    public void updateFood() {
        Date currentDate = new Date();
        foodService.updateFood(1L, "whiskas", "da", currentDate);
        List<Food> foodList = foodService.getFoodFromRepository();
        for (Food food: foodList) {
            if (food.getId().equals(1L)) {
                assertEquals(food.getName(), "whiskas");
                assertEquals(food.getProducer(), "da");
                return;
            }
        }
        fail();
    }

    @Test
    public void getNotExpiredFood() {
        List<Food> foodList = foodService.getNotExpiredFood();
        assertEquals("there should be 1 valid food", 1, foodList.size());
        assertEquals(foodList.get(0).getName(), "f1");
    }
}
