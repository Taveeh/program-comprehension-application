package core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.domain.Customer;
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
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void getCustomersFromRepository() {
        assertEquals("there should be 2 customers", 2, customerService.getCustomersFromRepository().size());
    }
    @Test
    public void addCustomer() {
        customerService.addCustomer("c3", "1234567890");
        assertEquals("there should be 3 customers", 3, customerService.getCustomersFromRepository().size());
    }
    @Test
    public void deleteCustomer() {
        customerService.deleteCustomer(1L);
        assertEquals("there should be 1 customers", 1, customerService.getCustomersFromRepository().size());
    }

    @Test
    public void updateCustomer() {
        customerService.updateCustomer(1L, "marcel", "0987654321");
        List<Customer> customerList = customerService.getCustomersFromRepository();
        for (Customer customer: customerList) {
            if (customer.getId().equals(1L)) {
                assertEquals("Name should be marcel", "marcel", customer.getName());
                assertEquals("0987654321", customer.getPhoneNumber());
                return;
            }
        }
        fail();
    }

}

