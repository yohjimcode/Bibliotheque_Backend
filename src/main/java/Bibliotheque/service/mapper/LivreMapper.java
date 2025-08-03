package Bibliotheque.service.mapper;

import Bibliotheque.infra.model.LivreModel;
import Bibliotheque.rest.dto.LivreDto;
import org.mapstruct.Mapper;

import java.util.List;

// mvn clean -> pour nettoyer
@Mapper(componentModel = "spring")
public interface LivreMapper {

    LivreDto toDto(LivreModel model);

    LivreModel toModel(LivreDto dto);

    List<LivreDto> toDtoList(List<LivreModel> models);

}
