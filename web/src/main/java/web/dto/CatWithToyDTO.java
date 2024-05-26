package web.dto;

import core.domain.Toy;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Builder
public class CatWithToyDTO extends BaseDTO<Long>{
    String name, breed;
    Integer catYears;
    Toy favoriteToy;
}
