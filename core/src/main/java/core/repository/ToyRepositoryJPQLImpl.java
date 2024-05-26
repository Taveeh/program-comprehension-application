package core.repository;

import core.domain.Toy;
import core.repository.custom.ToyCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ToyRepositoryJPQLImpl extends CustomRepositorySupport implements ToyCustomRepository {
    @Override
    public List<Toy> getToysThatStartWith(String prefix) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct t from Toy t where t.name like :prefix", Toy.class
        );
        query.setParameter("prefix", prefix + "%");
        return (List<Toy>) query.getResultList();

    }

    @Override
    public List<Toy> getToysThatEndWith(String suffix) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct t from Toy t where t.name like :suffix", Toy.class
        );
        query.setParameter("suffix", "%" + suffix);
        return (List<Toy>) query.getResultList();
    }
}
