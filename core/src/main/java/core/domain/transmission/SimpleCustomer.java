package core.domain.transmission;

import core.domain.BaseEntity;
import jdk.jfr.DataAmount;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SimpleCustomer extends BaseEntity<Long> {
    String name;
    String phoneNumber;

    public SimpleCustomer(Long id, String name, String phoneNumber) {
        this.setId(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
