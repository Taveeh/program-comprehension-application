package core.service;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.domain.Toy;
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
public class ToyServiceTest {
    @Autowired
    private ToyService toyService;

    @Test
    public void getToysFromRepository() {
        List<Toy> toys = toyService.getToysFromRepository();
        System.out.println(toys);
        assertEquals("there should be no toy", 0, toys.size());
    }

    @Test
    public void addToy() {
        toyService.addToy("jucarie", 2);
        List<Toy> toys = toyService.getToysFromRepository();
        System.out.println(toys);
        assertEquals("there should be 1 toy", 1, toys.size());

    }

    @Test
    public void addToyToCat() {
        toyService.addToy("jucarie", 2);
        toyService.addToyToCat(1L, 0L);
        assertNotNull("there should be a cat", toyService.getToysFromRepository().get(0).getCat());
    }
}
