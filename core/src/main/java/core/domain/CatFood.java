package core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class CatFood extends BaseEntity<CatFoodPrimaryKey>{
    public CatFood(){

    }
    @JsonBackReference(value = "cat-reference")
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("catId")
    @JoinColumn(name = "catId")
    Cat cat;

    @JsonBackReference(value = "food-reference")
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("foodId")
    @JoinColumn(name = "foodId")
    Food food;

    //public CatFood(Long catId, Long foodId) {
    //    this.setId(new CatFoodPrimaryKey(catId, foodId));
    //}

    public CatFood(CatFoodPrimaryKey catFoodPrimaryKey, Cat cat, Food food){
        this.setId(catFoodPrimaryKey);
        this.cat = cat;
        this.food = food;
    }

    public Long getCatId() {
        return this.getId().getCatId();
    }

    public Long getFoodId() {
        return this.getId().getFoodId();
    }

    public void setCatId(Long catId){
        this.getId().setCatId(catId);
    }

    public void setFoodId(Long foodId){
        this.getId().setFoodId(foodId);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CatFood && this.getId().equals(((CatFood) obj).getId());
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}

