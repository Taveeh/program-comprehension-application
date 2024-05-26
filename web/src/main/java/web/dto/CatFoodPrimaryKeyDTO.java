package web.dto;

import core.domain.Pair;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Builder
public class CatFoodPrimaryKeyDTO{
    Long catId, foodId;
}
