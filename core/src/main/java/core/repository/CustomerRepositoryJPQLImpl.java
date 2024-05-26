package core.repository;

import core.domain.Customer;
import core.domain.Purchase;
import core.exceptions.PetShopException;
import core.repository.CustomRepositorySupport;
import core.repository.custom.CustomerCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CustomerRepositoryJPQLImpl extends CustomRepositorySupport implements CustomerCustomRepository{
    @Override
    public void addPurchaseToCustomer(Purchase purchase, Long customerId) {
        EntityManager entityManager = getEntityManager();
        Customer customer = entityManager.getReference(Customer.class, customerId);
        if (customer == null) {
            throw new PetShopException("Customer does not exist");
        }
        customer.addPurchase(purchase);
        entityManager.persist(customer);

    }

    @Override
    public List<Customer> showCustomersThatHaveGivenPhonePrefix(String prefix) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct c from Cat c where c.name like :prefix", Customer.class
        );
        query.setParameter("prefix", prefix + "%");
        return (List<Customer>) query.getResultList();
    }
}
