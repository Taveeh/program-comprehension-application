package core.repository.custom;

import core.domain.Toy;

import java.util.List;

public interface ToyCustomRepository {
    List<Toy> getToysThatStartWith(String prefix);

    List<Toy> getToysThatEndWith(String suffix);
}
