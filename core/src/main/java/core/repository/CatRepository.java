package core.repository;

import core.domain.Cat;
import core.repository.custom.CatCustomRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CatRepository extends Repository<Cat, Long>, CatCustomRepository {
    @Query("select distinct c from Cat c")
    @EntityGraph(value = "catWithToy", type = EntityGraph.EntityGraphType.LOAD)
    List<Cat> findAllWithToys();

    @Query("select distinct c from Cat c ")
    @EntityGraph(value = "catWithCatFoodAndFood", type = EntityGraph.EntityGraphType.LOAD)
    List<Cat> findAllWithCatFood();
}
