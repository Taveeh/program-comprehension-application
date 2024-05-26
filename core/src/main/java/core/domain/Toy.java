package core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "toyWithCat",
                attributeNodes = @NamedAttributeNode(value = "cat"))
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"cat"})
@Entity
public class Toy extends BaseEntity<Long> {
    String name;
    int size;

    @JsonBackReference(value = "cat-reference1")
    @OneToOne(mappedBy = "favoriteToy", fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
    private Cat cat = null;

    public Toy(Long id, String name, int size) {
        this.setId(id);
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + " Toy{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", cat=" + cat +
                '}';
    }
}
