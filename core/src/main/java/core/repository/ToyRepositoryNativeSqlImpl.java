package core.repository;

import core.domain.Toy;
import core.repository.custom.CustomerCustomRepository;
import core.repository.custom.ToyCustomRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ToyRepositoryNativeSqlImpl extends CustomRepositorySupport implements ToyCustomRepository {

    @Override
    public List<Toy> getToysThatStartWith(String prefix) {
        return getToys("%", prefix);
    }


    @Override
    public List<Toy> getToysThatEndWith(String suffix) {
        return getToys(suffix, "%");
    }

    private List<Toy> getToys(String suffix, String s) {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "select distinct {t.*} " +
                        "from Toy t " +
                        "where t.name like :value"
        )
                .addEntity(Toy.class)
                .setParameter("value", s + suffix)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Toy>) query.getResultList();
    }
}
