package core.repository;

import core.domain.Food;
import core.repository.custom.FoodCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class FoodRepositoryJPQLImpl extends CustomRepositorySupport implements FoodCustomRepository {
    @Override
    public List<Food> findAllFoodNotExpired() {
        Date currentDate = new Date();
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct f from Food f where f.expirationDate < current_date ",
                Food.class
        );
        return (List<Food>) query.getResultList();
    }

    @Override
    public List<Food> findAllFoodFromProducer(String producer) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select  distinct f from Food f where f.producer = :name",
                Food.class
        );
        query.setParameter("name", producer);
        return (List<Food>) query.getResultList();
    }
}
