package core.domain.transmission;

import core.domain.BaseEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SimpleCat extends BaseEntity<Long> {
    String name, breed;
    Integer catYears;

    public SimpleCat(Long id, String name, String breed, Integer catYears) {
        this.setId(id);
        this.name = name;
        this.breed = breed;
        this.catYears = catYears;
    }
}
