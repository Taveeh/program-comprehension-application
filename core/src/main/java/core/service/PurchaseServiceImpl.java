package core.service;

import core.domain.*;
import core.exceptions.PetShopException;
import core.repository.CatRepository;
import core.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    public static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private CatRepository catsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public void addPurchase(Long catId, Long customerId, int price, Date dateAcquired, int review) {
        logger.trace("addPurchase - method entered - catId: " + catId + ", customerId: " + customerId + ", price: " + price + ", date acquired: " + dateAcquired + ", review: " + review);
        Optional<Cat> cat = catsRepository.findAllWithPurchases().stream().filter(c -> c.getId().equals(catId)).findFirst();
        cat.ifPresentOrElse((Cat c) -> {
            Optional<Customer> customer = customerRepository.findAllWithPurchaseAndCat().stream().filter(cu -> cu.getId().equals(customerId)).findFirst();
            customer.ifPresentOrElse((Customer cs) -> {
                Purchase purchase = new Purchase(new CustomerPurchasePrimaryKey(customerId, catId), cs, c, price, dateAcquired, review);
                catsRepository.addPurchaseToCat(purchase, c.getId());
                customerRepository.addPurchaseToCustomer(purchase, cs.getId());
            }, () -> {
                throw new PetShopException("Purchase id does not exist!");
            });
        }, () -> {
            throw new PetShopException("Cat id does not exist!");
        });
        logger.trace("addPurchase - method finished");

    }

    @Override
    public List<Purchase> getPurchasesFromRepository() {
        logger.trace("getPurchasesFromRepository - method entered");
        Set<Purchase> purchases = new HashSet<>();
        catsRepository.findAllWithPurchases().stream().map(Cat::getPurchases).forEach(purchases::addAll);
        logger.trace("getPurchasesFromRepository: " + purchases.toString());
        return new ArrayList<>(purchases);
    }


    @Override
    @Transactional
    public void deletePurchase(Long catId, Long customerId) {
        logger.trace("delete purchase - method entered - catId: " + catId + ", customerId: " + customerId);
        catsRepository.findById(catId)
                .ifPresentOrElse(
                        cat -> {
                            var catFood = cat.getPurchases()
                                    .stream()
                                    .filter(cf -> cf.getCustomerId().equals(customerId))
                                    .findFirst();
                            catFood.ifPresent(
                                    catFoodToRemove -> cat.getPurchases().remove(catFoodToRemove)
                            );

                        },
                        () -> {
                            throw new PetShopException("Purchase Does Not Exist");
                        }
                );
        customerRepository.findById(customerId)
                .ifPresentOrElse(
                        food -> {
                            var catFood = food.getPurchaseSet()
                                    .stream()
                                    .filter(cf -> cf.getCatId().equals(catId))
                                    .findFirst();

                            catFood.ifPresent(
                                    catFoodToRemove -> food.getPurchaseSet().remove(catFoodToRemove)
                            );
                        },
                        () -> {
                            throw new PetShopException("Purchase does not exist");
                        }
                );
        logger.trace("delete purchase - method finished");
    }

    @Override
    @Transactional
    public void updatePurchase(Long catId, Long customerId, int newReview) {
        logger.trace("updatePurchase - method entered - catId: " + catId + ", customerId: " + customerId + " ,newReview" + newReview);
        catsRepository.findById(catId).ifPresentOrElse(
                (Cat c) -> c.getPurchases().stream().filter((Purchase p) -> p.getCustomerId().equals(customerId))
                        .findFirst().ifPresentOrElse(
                        purchase -> {
                            purchase.setReview(newReview);
                        },
                        () -> {
                            throw new PetShopException("Customer id does not exist");
                        }
                ),
                () -> {
                    throw new PetShopException("Cat id does not exist");
                }
        );

        customerRepository.findById(customerId).ifPresentOrElse(
                (Customer c) -> c.getPurchaseSet().stream().filter((Purchase p) -> p.getCatId().equals(catId))
                        .findFirst().ifPresentOrElse(
                        purchase -> {
                            purchase.setReview(newReview);
                        },
                        () -> {
                            throw new PetShopException("Cat id does not exist");
                        }
                ),
                () -> {
                    throw new PetShopException("Customer id does not exist");
                }
        );
        logger.trace("updatePurchase - method finished");
    }

    @Override
    public Set<Customer> filterCustomersThatBoughtBreedOfCat(String breed) {

        logger.trace("filterCustomersThatBoughBreedOfCat - method entered - breed: " + breed);

        Set<Customer> customers = getPurchasesFromRepository().stream()
                .filter(purchase -> purchase.getCat().getBreed().equals(breed))
                .map(Purchase::getCustomer)
                .collect(Collectors.toSet());
        logger.trace("filterCustomersThatBoughBreedOfCat - method finished - " + customers);
        return customers;
    }

    @Override
    public List<Purchase> filterPurchasesWithMinStars(int minStars) {
        logger.trace("filterPurchasesWithMinStars - method entered - min stars: " + minStars);
        List<Purchase> purchases = getPurchasesFromRepository().stream()
                .filter(purchase -> purchase.getReview() >= minStars)
                .collect(Collectors.toList());
        logger.trace("filterPurchasesWithMinStars - method finished - " + purchases);
        return purchases;
    }

    @Override
    public List<Pair<Customer, Integer>> reportCustomersSortedBySpentCash() {
        logger.trace("reportCustomersSortedBySpentCash - method entered");
        List<Pair<Customer, Integer>> toReturn = customerRepository.getCustomersSortedSpentCashInterface().stream()
                .map(obj -> new Pair<>(new Customer(((BigInteger) obj[0]).longValue(), (String) obj[1], (String) obj[2]), ((BigInteger) obj[3]).intValue()))
                .collect(Collectors.toList());
        logger.trace("reportCustomersSortedBySpentCash - method finished - " + toReturn);
        return toReturn;
    }
}
