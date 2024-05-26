package web.converter;

import core.domain.BaseEntity;
import core.domain.Toy;
import org.springframework.stereotype.Component;
import web.dto.BaseDTO;
import web.dto.ToyDTO;

@Component
public class ToyConverter extends BaseConverter<Long, Toy, ToyDTO> {

    @Override
    public Toy convertDtoToModel(ToyDTO dto) {
        var model = new Toy();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setSize(dto.getSize());
        return model;
    }

    @Override
    public ToyDTO convertModelToDto(Toy toy) {
        var toyDto = new ToyDTO(
                toy.getName(),
                toy.getSize()
        );
        toyDto.setId(toy.getId());
        return toyDto;
    }
}
