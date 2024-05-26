package core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.domain.*;
import core.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class CatFoodServiceTest extends BasicTest {
    @Autowired
    private CatFoodService catFoodService;

    @Autowired
    CatService catService;

    @Autowired
    FoodService foodService;

    Cat cat;
    Toy toy;
    Purchase firstPurchase;
    Food food;
    Customer customer;

    CatRepository catRepository;
    CatRepositoryCriteriaImpl catRepositoryCriteria;
    CatRepositoryJPQLImpl catRepositoryJPQL;
    CatRepositoryNativeSqlImpl catRepositoryNativeSql;

    FoodRepository foodRepository;
    FoodRepositoryCriteriaImpl foodRepositoryCriteria;
    FoodRepositoryJPQLImpl foodRepositoryJPQL;
    FoodRepositoryNativeSqlImpl foodRepositoryNativeSql;

    ToyRepository toyRepository;
    ToyRepositoryCriteriaImpl toyRepositoryCriteria;
    ToyRepositoryJPQLImpl toyRepositoryJPQL;
    ToyRepositoryNativeSqlImpl toyRepositoryNativeSql;

    public void findData() {
        catRepository.findAll();
        catRepositoryCriteria.findAllWithPurchases();
        catRepositoryJPQL.findAllWithPurchases();
        catRepositoryNativeSql.findAllWithPurchases();
        foodRepository.findAllWithCatFood();
        foodRepositoryCriteria.findAllFoodNotExpired();
        foodRepositoryJPQL.findAllFoodNotExpired();
        foodRepositoryNativeSql.findAllFoodNotExpired();
        toyRepository.getToysWithCat();
        toyRepositoryCriteria.getToysThatEndWith("");
        toyRepositoryJPQL.getToysThatEndWith("");
        toyRepositoryNativeSql.getToysThatEndWith("");
        catRepository.flush();
        foodRepository.flush();
        toyRepository.flush();
    }
    @Test
    public void getCatFoodFromRepository() {
        assertEquals("there should be no cat food", 0, catFoodService.getCatFoodFromRepository().size());
    }

    @Test
    public void addCatFood() {
        catFoodService.addCatFood(1L, 1L);
        assertEquals("there should be 1 cat food", 1, catFoodService.getCatFoodFromRepository().size());
        List<CatFood> catFoods = catFoodService.getCatFoodFromRepository();
        cat = catService.getCatsFromRepository()
                .stream().filter(it -> it.getId().equals(1L)).findFirst().get();
        food = foodService.getFoodFromRepository()
                .stream().filter(it -> it.getId().equals(1L)).findFirst().get();
        toy = cat.getFavoriteToy();
        Set<Purchase> purchaseSet = cat.getPurchases();
        firstPurchase = purchaseSet.stream().findFirst().get();
        List<Customer> customers = purchaseSet.stream().map(Purchase::getCustomer).collect(Collectors.toList());
        customer = customers.get(0);
        catFoodService.deleteCatFood(1L, 1L);
    }

    @Test
    public void deleteCatFood() {
        catFoodService.addCatFood(1L, 1L);
        catFoodService.deleteCatFood(1L, 1L);
        assertEquals("there should be no cat food", 0, catFoodService.getCatFoodFromRepository().size());
    }

    @Test
    public void filterCatsThatEatCertainFood() {
        catFoodService.addCatFood(1L, 1L);
        catFoodService.addCatFood(2L, 2L);
        assertEquals("there should be 1 cat food", 1, catFoodService.filterCatsThatEatCertainFood(1L).size());
        catFoodService.deleteCatFood(1L, 1L);
        catFoodService.deleteCatFood(2L, 2L);
    }
}
