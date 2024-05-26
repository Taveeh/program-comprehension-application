package core.repository;

import core.domain.Food;
import core.repository.custom.CustomerCustomRepository;
import core.repository.custom.FoodCustomRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class FoodRepositoryNativeSqlImpl extends CustomRepositorySupport implements FoodCustomRepository {
    @Override
    public List<Food> findAllFoodNotExpired() {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "select distinct {f.*} " +
                        "from Food f " +
                        "where f.expirationdate < :date"
        )
                .addEntity("f", Food.class)
                .setParameter("date", new Date())
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Food>) query.getResultList();
    }

    @Override
    public List<Food> findAllFoodFromProducer(String producer) {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "select distinct {f.*} " +
                        "from Food f " +
                        "where f.producer = :producer"
        )
                .addEntity("f", Food.class)
                .setParameter("producer", producer)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Food>) query.getResultList();
    }
}
