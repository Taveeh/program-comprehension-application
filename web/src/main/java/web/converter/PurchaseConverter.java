package web.converter;

import core.domain.Cat;
import core.domain.Customer;
import core.domain.CustomerPurchasePrimaryKey;
import core.domain.Purchase;
import core.domain.transmission.SimpleCat;
import core.domain.transmission.SimpleCustomer;
import org.springframework.stereotype.Component;
import web.dto.PurchaseDTO;

@Component
public class PurchaseConverter extends BaseConverter<CustomerPurchasePrimaryKey, Purchase, PurchaseDTO> {
    @Override
    public Purchase convertDtoToModel(PurchaseDTO dto) {
        var model = new Purchase();
        model.setId(dto.getId());
        model.setCat(new Cat(dto.getCat().getId(), dto.getCat().getName(), dto.getCat().getBreed(), dto.getCat().getCatYears()));
        model.setCustomer(new Customer(dto.getCustomer().getId(), dto.getCustomer().getName(), dto.getCustomer().getPhoneNumber()));
        model.setDateAcquired(dto.getDateAcquired());
        model.setPrice(dto.getPrice());
        model.setReview(dto.getReview());
        return model;
    }

    @Override
    public PurchaseDTO convertModelToDto(Purchase purchase) {
        var purchaseDTO = new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setCat(new SimpleCat(purchase.getCat().getId(), purchase.getCat().getName(), purchase.getCat().getBreed(), purchase.getCat().getCatYears()));
        purchaseDTO.setCustomer(new SimpleCustomer(purchase.getCustomer().getId(), purchase.getCustomer().getName(), purchase.getCustomer().getPhoneNumber()));
        purchaseDTO.setDateAcquired(purchase.getDateAcquired());
        purchaseDTO.setPrice(purchase.getPrice());
        purchaseDTO.setReview(purchase.getReview());
        return purchaseDTO;
    }
}
