package core.service;

import core.domain.Cat;
import core.domain.Toy;
import core.repository.CatRepository;
import core.repository.ToyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.max;

@Service
public class ToyServiceImpl implements ToyService {
    public static final Logger logger = LoggerFactory.getLogger(ToyServiceImpl.class);

    @Autowired
    private ToyRepository toysRepository;

    @Autowired
    private CatRepository catsRepository;

    @Override
    public void addToy(String name, int size) {
        logger.trace("add toy - method entered - name: " + name + ", size: " + size);
        long id = 0;
        for (Toy toy : this.toysRepository.findAll()) {
            id = max(id, toy.getId() + 1);
        }
        Toy toyToBeAdded = new Toy(id, name, size);
        toysRepository.save(toyToBeAdded);
        logger.trace("add toy - method finished");
    }

    @Override
    public List<Toy> getToysFromRepository() {
        logger.trace("getToysFromRepository - method entered");
        List<Toy> toys = toysRepository.getToysWithCat();
        logger.trace("getToysFromRepository: " + toys.toString());
        return toys;
    }

    @Override
    @Transactional
    public void addToyToCat(Long catId, Long toyId) {
        catsRepository.findById(catId)
                .ifPresent(cat -> toysRepository.findById(toyId)
                        .ifPresent(toy -> {
                            if (toy.getCat() == null && cat.getFavoriteToy() == null) {
                                cat.setFavoriteToy(toy);
                                toy.setCat(cat);
                            }
                        }));
    }

}
