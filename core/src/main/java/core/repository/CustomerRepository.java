package core.repository;

import core.domain.Customer;
import core.repository.custom.CustomerCustomRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends Repository<Customer, Long>, CustomerCustomRepository {
    @Query(value = "select c.* as customerName, sum(p.price) as totalCash from customer c " +
            "inner join purchase p " +
            "on c.id = p.customerId " +
            "group by c.id " +
            "order by sum(p.price) desc", nativeQuery = true)
    List<Object[]> getCustomersSortedSpentCashInterface();

    @Query("select distinct c from Customer c")
    @EntityGraph(value = "customerWithPurchaseAndCat", type = EntityGraph.EntityGraphType.LOAD)
    List<Customer> findAllWithPurchaseAndCat();

    @Query("select distinct c from Customer c where c.purchaseSet.size > :size")
    @EntityGraph(value = "customerWithPurchase", type = EntityGraph.EntityGraphType.LOAD)
    List<Customer> findCustomersWithPurchasesMoreThan(@Param("size") Integer purchaseNumber);
}
