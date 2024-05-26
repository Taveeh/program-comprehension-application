package core.service;

import core.domain.*;
import core.exceptions.PetShopException;
import core.repository.CatRepository;
import core.repository.FoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatFoodServiceImpl implements CatFoodService {
    public static final Logger logger = LoggerFactory.getLogger(CatFoodServiceImpl.class);

    @Autowired
    private CatRepository catsRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    @Transactional
    public void addCatFood(Long catId, Long foodId) {
        logger.trace("add catFood - method entered - catId: " + catId + ", foodId: " + foodId);
        Optional<Cat> cat = catsRepository.findAllWithCatFood().stream().filter(c -> c.getId().equals(catId)).findFirst();
        cat.ifPresentOrElse((Cat c) -> {
            Optional<Food> food = foodRepository.findAllWithCatFood().stream().filter(f -> f.getId().equals(foodId)).findFirst();
            food.ifPresentOrElse((Food f) -> {
                CatFood catFood = new CatFood(new CatFoodPrimaryKey(catId, foodId), c, f);
                c.getCatFoods().add(catFood);
                f.getCatFoodList().add(catFood);
            }, () -> {
                throw new PetShopException("Food id does not exist!");
            });
        }, () -> {
            throw new PetShopException("Cat id does not exist!");
        });
        logger.trace("add catFood - method finished");
    }

    @Override
    public List<CatFood> getCatFoodFromRepository() {
        logger.trace("getCatFoodFromRepository - method entered");
        Set<CatFood> catFoodSet = new HashSet<>();
        catsRepository.findAllWithCatFood().stream().map(Cat::getCatFoods).forEach(catFoodSet::addAll);
        logger.trace("getCatFoodFromRepository: " + catFoodSet.toString());
        return new ArrayList<>(catFoodSet);
    }


    @Override
    public List<Pair<Cat, Food>> getCatFoodJoin() {
        logger.trace("getCatFoodJoin - method entered");
        List<Pair<Cat, Food>> join = new LinkedList<>();
        getCatFoodFromRepository().forEach(catFood -> join.add(new Pair<>(
                catsRepository.findById(catFood.getCatId()).orElseThrow(() -> new PetShopException("Cat does not exist")),
                foodRepository.findById(catFood.getFoodId()).orElseThrow(() -> new PetShopException("Food does not exist"))
        )));
        logger.trace("getCatFoodJoin: " + join.toString());
        return join;
    }

    @Override
    @Transactional
    public void deleteCatFood(Long catId, Long foodId) {
        logger.trace("deleteCatFood - method entered - catId: " + catId + " - foodId: " + foodId);
        catsRepository.findById(catId)
                .ifPresentOrElse(
                        cat -> {
                            var catFood = cat.getCatFoods()
                                    .stream()
                                    .filter(cf -> cf.getFoodId().equals(foodId))
                                    .findFirst();
                            catFood.ifPresent(
                                    catFoodToRemove -> cat.getCatFoods().remove(catFoodToRemove)
                            );

                        },
                        () -> {
                            throw new PetShopException("CatFood Does Not Exist");
                        }
                );
        foodRepository.findById(foodId)
                .ifPresentOrElse(
                        food -> {
                            var catFood = food.getCatFoodList()
                                    .stream()
                                    .filter(cf -> cf.getCatId().equals(catId))
                                    .findFirst();

                            catFood.ifPresent(
                                    catFoodToRemove -> food.getCatFoodList().remove(catFoodToRemove)
                            );
                        },
                        () -> {
                            throw new PetShopException("CatFood does not exist");
                        }
                );

        logger.trace("deleteCatFood - method finished");
    }

    @Override
    @Transactional
    public void updateCatFood(Long catId, Long foodId, Long newFoodId) {
        logger.trace("updateCatFood - method entered - catId: " + catId + " - foodId: " + foodId + " - newFoodId: " + newFoodId);
        catsRepository.findById(catId).orElseThrow(() -> new PetShopException("Cat does not exist"));
        foodRepository.findById(foodId).orElseThrow(() -> new PetShopException("Food does not exist"));
        foodRepository.findById(newFoodId).orElseThrow(() -> new PetShopException("New food does not exist"));
        deleteCatFood(catId, foodId);
        addCatFood(catId, newFoodId);
        logger.trace("updateCatFood - method finished");
    }


    @Override
    public List<CatFood> filterCatsThatEatCertainFood(Long foodId) {
        logger.trace("filterCatsThatEatCertainFood - method entered - foodId: " + foodId);
        Food food = new Food();
        food.setId(foodId);
        Set<CatFood> catFoods = new HashSet<>();
        catsRepository.filterCatsThatEatCertainFood(food)
                .forEach(cat -> {
                    System.out.println(cat);
                    cat.getCatFoods()
                            .stream()
                            .filter(catFood -> catFood.getFoodId()
                                    .equals(foodId))
                            .forEach(catFoods::add);
                });
        logger.trace("filterCatsThatEatCertainFood: " + catFoods.toString());
        return new ArrayList<>(catFoods);
    }
}
