package core.repository;

import core.domain.Cat;
import core.domain.Food;
import core.domain.Purchase;
import core.repository.custom.CatCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

public class CatRepositoryCriteriaImpl extends CustomRepositorySupport implements CatCustomRepository {
    @Override
    public List<Cat> filterCatsThatEatCertainFood(Food food) {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cat> query = criteriaBuilder.createQuery(Cat.class);
        query.distinct(true);
        Root<Cat> root = query.from(Cat.class);
        Join<Object, Object> catCatFoodFetch = (Join<Object, Object>) root.fetch("catFoods", JoinType.LEFT);
        System.out.println(catCatFoodFetch.getAttribute().getName());
        query.where(criteriaBuilder.equal(catCatFoodFetch.get("food"), food));
        catCatFoodFetch.fetch("food", JoinType.LEFT);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void addPurchaseToCat(Purchase purchase, Long catId) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cat> query = criteriaBuilder.createQuery(Cat.class);
        query.distinct(true);
        Root<Cat> root = query.from(Cat.class);
        query.where(criteriaBuilder.equal(root.get("id"), catId));

        List<Cat> cats = entityManager.createQuery(query).getResultList();
        cats.forEach(cat -> cat.addPurchase(purchase));
    }

    @Override
    public List<Cat> findAllWithPurchases() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cat> query = criteriaBuilder.createQuery(Cat.class);
        query.distinct(true);
        Root<Cat> root = query.from(Cat.class);
        Fetch<Cat, Purchase> catPurchaseFetch = root.fetch("purchases", JoinType.LEFT);
        catPurchaseFetch.fetch("customer", JoinType.LEFT);
        return entityManager.createQuery(query).getResultList();
    }
}
