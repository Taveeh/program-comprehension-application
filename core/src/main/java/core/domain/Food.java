package core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jdk.jfr.Name;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "foodWithCatFoodAndCat",
                attributeNodes = @NamedAttributeNode(value = "catFoodList",
                        subgraph = "catFoodWithCats"),
                subgraphs = @NamedSubgraph(name = "catFoodWithCats",
                        attributeNodes = @NamedAttributeNode(value = "cat")))
})
@Entity
public class Food extends BaseEntity<Long> {
    String name, producer;
    Date expirationDate;

    public Set<CatFood> getCatFoodList() {
        return catFoodList;
    }

    public Food() {

    }

    public Food(Long id, String name, String producer, Date expirationDate) {
        this.setId(id);
        this.name = name;
        this.producer = producer;
        this.expirationDate = expirationDate;
    }

    public void setCatFoodList(Set<CatFood> catFoodList) {
        this.catFoodList = catFoodList;
    }

    @JsonManagedReference(value = "food-reference")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food", cascade = {CascadeType.ALL}, orphanRemoval = true)
    Set<CatFood> catFoodList = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Food{name: " + this.name +
                "; producer: " + this.producer +
                "; expirationDate: " + this.expirationDate +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Food && this.getId().equals(((Food) obj).getId());
    }
}
