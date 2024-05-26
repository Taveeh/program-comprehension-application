package web.dto;

import core.domain.Cat;
import core.domain.Customer;
import core.domain.CustomerPurchasePrimaryKey;
import core.domain.transmission.SimpleCat;
import core.domain.transmission.SimpleCustomer;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PurchaseDTO extends BaseDTO<CustomerPurchasePrimaryKey> {
    SimpleCustomer customer;
    SimpleCat cat;
    int price;
    Date dateAcquired;
    int review;
}
