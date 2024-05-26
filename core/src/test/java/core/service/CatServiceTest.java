package core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import core.ITConfig;
import core.domain.Cat;
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
public class CatServiceTest {
    @Autowired
    private CatService catService;

    @Test
    public void getCatsFromRepository() throws Exception {
        List<Cat> catList = catService.getCatsFromRepository();
        assertEquals("there should be 4 cats", 4, catList.size());
        for (int i = 0; i < catList.size(); i++) {
            Cat cat = catList.get(i);
            assertEquals(cat.getId(), i);
        }
    }

    @Test
    public void getCatsWithToys() throws Exception {
        List<Cat> catList = catService.getCatsWithToys();
        assertEquals("there should be 4 cats", 4, catList.size());
        Toy toy = catList.get(0).getFavoriteToy();
        assertNotNull(toy);
    }

    @Test
    public void addCat() throws Exception {
        catService.addCat("c5", "b5", 5);
        List<Cat> cats = catService.getCatsFromRepository();
        System.out.println(cats);
        assertEquals("there should be 5 cats", 5, cats.size());
        catService.deleteCat(5L);
    }

    @Test
    public void deleteCat() throws Exception {
        catService.deleteCat(1L);
        assertEquals("there should be 3 cats", 3, catService.getCatsFromRepository().size());
    }

    @Test
    public void updateCat() throws Exception {
        catService.updateCat(1L, "kitty", "kitty", 3);
        List<Cat> catList = catService.getCatsFromRepository();
        for(Cat cat: catList) {
            if (cat.getId().equals(1L)) {
                assertEquals(cat.getName(), "kitty");
                assertEquals(cat.getBreed(), "kitty");
                assertEquals(cat.getCatYears(), Integer.valueOf(3));
                return;
            }
        }
        fail();
    }

}
