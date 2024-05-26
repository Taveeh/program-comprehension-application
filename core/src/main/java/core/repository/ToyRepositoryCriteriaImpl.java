package core.repository;

import core.domain.Toy;
import core.repository.custom.ToyCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ToyRepositoryCriteriaImpl extends CustomRepositorySupport implements ToyCustomRepository {
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Toy> query = criteriaBuilder.createQuery(Toy.class);
        query.distinct(true);
        Root<Toy> root = query.from(Toy.class);
        query.where(criteriaBuilder.like(root.get("name"), s + suffix));
        return entityManager.createQuery(query).getResultList();
    }
}
