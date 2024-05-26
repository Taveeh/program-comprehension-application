package core.repository;

import core.domain.Toy;
import core.repository.custom.ToyCustomRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToyRepository extends Repository<Toy, Long>, ToyCustomRepository {
    @Query("select distinct t from Toy t")
    @EntityGraph(value = "toyWithCat", type = EntityGraph.EntityGraphType.LOAD)
    List<Toy> getToysWithCat();

    @Query("select distinct t from Toy t where t.size > :value")
    @EntityGraph(value = "toyWithCat", type = EntityGraph.EntityGraphType.LOAD)
    List<Toy> getAllWithCatWhereSizeGreater(@Param("value") Integer value);
}
