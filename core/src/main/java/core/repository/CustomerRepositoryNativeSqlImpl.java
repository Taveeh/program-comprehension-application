package core.repository;

import core.domain.Customer;
import core.domain.Purchase;
import core.repository.custom.CustomerCustomRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerRepositoryNativeSqlImpl extends CustomRepositorySupport implements CustomerCustomRepository {

    @Override
    @Transactional
    public void addPurchaseToCustomer(Purchase purchase, Long customerId) {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "insert into Purchase (catid, customerid, dateacquired, price, review) " +
                        "values (?, ?, ?, ?, ?)")
                .setParameter(1, purchase.getCatId())
                .setParameter(2, purchase.getCustomerId())
                .setParameter(3, purchase.getDateAcquired())
                .setParameter(4, purchase.getPrice())
                .setParameter(5, purchase.getReview());
        query.executeUpdate();
    }

    @Override
    public List<Customer> showCustomersThatHaveGivenPhonePrefix(String prefix) {
        EntityManager entityManager = getEntityManager();
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(
                "select distinct {c.*} from Customer c where c.name like ?"
        ).setParameter(1, prefix + "%")
                .addEntity("c", Customer.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Customer>) query.getResultList();
    }
}
