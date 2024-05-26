package web.dto;

import core.domain.Customer;
import core.domain.transmission.SimpleCustomer;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Builder
public class CustomerSpentCashDTO implements Serializable {
    SimpleCustomer customer;
    Integer totalCash;
}
