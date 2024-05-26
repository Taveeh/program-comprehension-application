package core.service;

import core.domain.Cat;
import core.domain.Toy;

import java.util.List;


public interface ToyService {

    void addToy(String name, int size);

    List<Toy> getToysFromRepository();

    void addToyToCat(Long catId, Long toyId);

}
