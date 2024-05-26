package core.repository;

import core.domain.Cat;
import core.domain.Food;

import core.domain.Purchase;
import core.exceptions.PetShopException;
import core.repository.CustomRepositorySupport;
import core.repository.custom.CatCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CatRepositoryJPQLImpl extends CustomRepositorySupport implements CatCustomRepository {

    @Override
    public List<Cat> filterCatsThatEatCertainFood(Food food) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct c from Cat c " +
                        "left join fetch c.catFoods cf " +
                        "left join fetch cf.food f where f = :food",
                Cat.class
        );
        query.setParameter("food", food);
        return (List<Cat>) query.getResultList();
    }

    @Override
    public List<Cat> findAllWithPurchases() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct c from Cat c " +
                        "left join fetch c.purchases p " +
                        "left join fetch p.customer",
                Cat.class
        );
        return (List<Cat>) query.getResultList();
    }

    @Override
    public void addPurchaseToCat(Purchase purchase, Long catId) {
        EntityManager entityManager = getEntityManager();
        Cat cat = entityManager.getReference(Cat.class, catId);
        if (cat == null) {
            throw new PetShopException("Cat does not exist");
        }
        cat.addPurchase(purchase);
        entityManager.persist(cat);
    }


}
