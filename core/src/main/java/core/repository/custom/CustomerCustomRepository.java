package core.repository.custom;

import core.domain.Customer;
import core.domain.Purchase;

import java.util.List;

public interface CustomerCustomRepository {

    void addPurchaseToCustomer(Purchase purchase, Long customerId);

    List<Customer> showCustomersThatHaveGivenPhonePrefix(String prefix);
}
