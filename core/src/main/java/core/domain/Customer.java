package core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "customerWithPurchaseAndCat",
                attributeNodes = @NamedAttributeNode(value = "purchaseSet",
                        subgraph = "purchasesWithCat"),
                subgraphs = @NamedSubgraph(name = "purchasesWithCat",
                        attributeNodes = @NamedAttributeNode(value = "customer")
                )
        ),
        @NamedEntityGraph(name = "customerWithPurchase",
                attributeNodes = @NamedAttributeNode(value = "purchaseSet"))
})
public class Customer extends BaseEntity<Long> {
    String name;
    String phoneNumber;

    public Customer() {

    }

    public Customer(Long id, String name, String phoneNumber) {
        this.setId(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @JsonManagedReference(value = "customer-reference")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = {CascadeType.ALL})
    Set<Purchase> purchaseSet = new HashSet<>();

    public Set<Purchase> getPurchaseSet() {
        return purchaseSet;
    }

    public void setPurchaseSet(Set<Purchase> purchaseSet) {
        this.purchaseSet = purchaseSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() + " Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Customer && this.getId().equals(((Customer) obj).getId());
    }

    public void addPurchase(Purchase purchase) {
        this.purchaseSet.add(purchase);
    }
}
