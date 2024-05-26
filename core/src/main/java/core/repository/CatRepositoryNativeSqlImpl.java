package core.repository;

import core.domain.Cat;
import core.domain.Food;
import core.domain.Purchase;
import core.repository.custom.CatCustomRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public class CatRepositoryNativeSqlImpl extends CustomRepositorySupport implements CatCustomRepository {

    @Override
    @Transactional
    public List<Cat> filterCatsThatEatCertainFood(Food food) {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "select distinct {c.*},{cf.*},{f.*} " +
                        "from Cat c " +
                        "left join CatFood cf on c.id = cf.catid " +
                        "left join Food f on f.id = cf.foodid " +
                        "where f.id = :id")
                .addEntity("c", Cat.class)
                .setParameter("id", food.getId())
                .addJoin("cf", "c.catFoods")
                .addJoin("f", "cf.food")
                .addEntity("c", Cat.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return (List<Cat>) query.getResultList();
    }

    @Override
    public void addPurchaseToCat(Purchase purchase, Long catId) {

    }

    @Override
    @Transactional
    public List<Cat> findAllWithPurchases() {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "select distinct {c.*}, {p.*}, {cs.*} " +
                        "from Cat c " +
                        "left join Purchase p on p.catid = c.id " +
                        "left join Customer cs on cs.id = p.customerid ")
                .addEntity("c", Cat.class)
                .addJoin("p", "c.purchases")
                .addJoin("cs", "p.customer")
                .addEntity("c", Cat.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Cat>) query.getResultList();
    }
}
