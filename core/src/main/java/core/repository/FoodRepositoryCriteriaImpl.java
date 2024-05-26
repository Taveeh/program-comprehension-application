package core.repository;

import core.domain.Food;
import core.repository.custom.FoodCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class FoodRepositoryCriteriaImpl extends CustomRepositorySupport implements FoodCustomRepository {
    @Override
    public List<Food> findAllFoodNotExpired() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Food> query = criteriaBuilder.createQuery(Food.class);
        query.distinct(true);
        Root<Food> root = query.from(Food.class);
        Path<Date> date = root.get("expirationDate");
        query.where(criteriaBuilder.lessThanOrEqualTo(date, new Date()));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Food> findAllFoodFromProducer(String producer) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Food> query = criteriaBuilder.createQuery(Food.class);
        query.distinct(true);
        Root<Food> root = query.from(Food.class);
        query.where(criteriaBuilder.equal(root.get("producer"), producer));
        return entityManager.createQuery(query).getResultList();
    }
}
