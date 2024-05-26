package core.repository;

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
public class ToyRepositoryTest {

    @Autowired
    private ToyRepository toyRepository;

    @Test
    public void getToysThatStartWith() {
        toyRepository.save(new Toy(0L, "jucarie", 12));
        toyRepository.save(new Toy(1L, "eiracuj", 21));

        assertEquals("there should be 1 cat", 1, toyRepository.getToysThatStartWith("ju").size());

        toyRepository.deleteById(1L);
        toyRepository.deleteById(0L);
    }

    @Test
    public void getToysThatEndWith() {
        toyRepository.save(new Toy(0L, "jucarie", 12));
        toyRepository.save(new Toy(1L, "eiracuj", 21));

        assertEquals("there should be 1 cat", 1, toyRepository.getToysThatEndWith("ie").size());

        toyRepository.deleteById(1L);
        toyRepository.deleteById(0L);
    }
}
