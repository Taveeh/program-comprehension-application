package core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

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
public class FoodRepositoryTest {
    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void findAllFoodFromProducer() {
        assertEquals("there should be 1 food", 1, foodRepository.findAllFoodFromProducer("p1").size());
    }

    @Test
    public void findAllFoodNotExpired() {
        assertEquals("there should be 1 food", 1, foodRepository.findAllFoodNotExpired().size());
    }

    @Test
    public void findAllFoodEatenByMoreThan() {
        assertEquals("there should be 0 food", 0, foodRepository.findAllFoodEatenByMoreThan(2).size());
    }
}
