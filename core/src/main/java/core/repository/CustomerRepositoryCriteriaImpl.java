package core.repository;

import core.domain.Cat;
import core.domain.Customer;
import core.domain.Purchase;
import core.repository.custom.CustomerCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerRepositoryCriteriaImpl extends CustomRepositorySupport implements CustomerCustomRepository {
    @Override
    public void addPurchaseToCustomer(Purchase purchase, Long customerId) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        query.distinct(true);
        Root<Customer> root = query.from(Customer.class);
        query.where(criteriaBuilder.equal(root.get("id"), customerId));

        List<Customer> cats = entityManager.createQuery(query).getResultList();
        cats.forEach(cat -> cat.addPurchase(purchase));
    }

    @Override
    public List<Customer> showCustomersThatHaveGivenPhonePrefix(String prefix) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        query.distinct(true);
        query.where(criteriaBuilder.like(root.get("phoneNumber"), prefix + "%"));
        return entityManager.createQuery(query).getResultList();
    }
}
