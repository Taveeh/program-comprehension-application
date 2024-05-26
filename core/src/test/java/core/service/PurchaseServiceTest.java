package core.service;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.domain.Customer;
import core.domain.Pair;
import core.domain.Purchase;
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
public class PurchaseServiceTest {
    @Autowired
    private PurchaseService purchaseService;

    @Test
    public void getPurchasesFromRepository() {
        List<Purchase> purchases = purchaseService.getPurchasesFromRepository();
        System.out.println(purchases);
        assertEquals("there should be no purchases", 0, purchases.size());
    }

    @Test
    public void addPurchase() {
        purchaseService.addPurchase(1L, 1L, 123, new Date(), 5);
        assertEquals("there should be 1 purchase", 1, purchaseService.getPurchasesFromRepository().size());
        purchaseService.deletePurchase(1L, 1L);
    }

    @Test
    public void deletePurchase() {
        purchaseService.addPurchase(1L, 1L, 123, new Date(), 5);
        purchaseService.deletePurchase(1L, 1L);
        assertEquals("there should be no purchases", 0, purchaseService.getPurchasesFromRepository().size());
    }

    @Test
    public void updatePurchase() {
        purchaseService.addPurchase(1L, 1L, 123, new Date(), 5);
        purchaseService.updatePurchase(1L, 1L, 1);
        assertEquals(purchaseService.getPurchasesFromRepository().get(0).getReview(), 1);
        purchaseService.deletePurchase(1L, 1L);
    }

    @Test
    public void filterCustomersThatBoughtBreedOfCat() {
        purchaseService.addPurchase(1L, 1L, 123, new Date(), 5);

        assertEquals("there should be one customer", 1, purchaseService.filterCustomersThatBoughtBreedOfCat("b1").size());
        purchaseService.deletePurchase(1L, 1L);
    }

    @Test
    public void filterPurchasesWithMinStars() {
        purchaseService.addPurchase(1L, 1L, 123, new Date(), 3);
        assertEquals("there should be 1 purchase", 1, purchaseService.filterPurchasesWithMinStars(3).size());
        assertEquals("there should be no purchase", 0, purchaseService.filterPurchasesWithMinStars(4).size());
        purchaseService.deletePurchase(1L, 1L);
    }

//    @Test
//    public void reportCustomersSortedBySpentCash() {
//        purchaseService.addPurchase(1L, 1L, 123, new Date(), 3);
//        purchaseService.addPurchase(2L, 2L, 12, new Date(), 4);
//        List<Pair<Customer, Integer>> customers = purchaseService.reportCustomersSortedBySpentCash();
//        assertEquals(Long.valueOf(1L), customers.get(0).getLeft().getId());
//        purchaseService.deletePurchase(1L, 1L);
//        purchaseService.deletePurchase(2L, 2L);
//    }
}
