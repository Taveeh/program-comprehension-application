package core.domain.transmission;

import core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SimpleFood extends BaseEntity<Long> {
    String name, producer;
    Date expirationDate;

    public SimpleFood(Long id, String name, String producer, Date expirationDate) {
        this.setId(id);
        this.name = name;
        this.producer = producer;
        this.expirationDate = expirationDate;
    }
}
