package web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Builder
public class SimplePurchasePrimaryKeyDTO {
    Long catId, customerId;
}
