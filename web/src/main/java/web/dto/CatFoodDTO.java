package web.dto;

import core.domain.Cat;
import core.domain.CatFoodPrimaryKey;
import core.domain.Food;
import core.domain.transmission.SimpleCat;
import core.domain.transmission.SimpleFood;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"cat", "food"})
@ToString(callSuper = true, exclude = {"cat", "food"})
@Builder
public class CatFoodDTO extends BaseDTO<CatFoodPrimaryKey>{
    SimpleCat cat;
    SimpleFood food;
}
