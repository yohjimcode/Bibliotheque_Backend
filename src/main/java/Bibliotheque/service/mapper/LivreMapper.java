package Bibliotheque.service.mapper;

import Bibliotheque.infra.model.AuteurModel;
import Bibliotheque.infra.model.LivreModel;
import Bibliotheque.rest.dto.AuteurDto;
import Bibliotheque.rest.dto.LivreDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// mvn clean -> pour nettoyer
@Component
public class LivreMapper {

    private final AuteurMapper auteurMapper;

    public LivreMapper(AuteurMapper auteurMapper) {
        this.auteurMapper = auteurMapper;
    }

    //model to Dto
    public LivreDto toDto(LivreModel livreModel){
        if (livreModel == null){
            return null;
        }
        LivreDto dto = new LivreDto();
        dto.setId(livreModel.getId());
        dto.setTitre(livreModel.getTitre());
        dto.setPrix(livreModel.getPrix());
        dto.setDateCreation(livreModel.getDateCreation());
        if (livreModel.getAuteurs() != null) {
            List<AuteurDto> auteursDto = livreModel.getAuteurs().stream()
                    .map(auteurMapper::toDto)
                    .collect(Collectors.toList());
            dto.setAuteurs(auteursDto);
        }
        return dto;
    }

    public LivreModel toModel(LivreDto livreDto){
        if(livreDto == null){
            return null;
        }
        LivreModel model = new LivreModel();
        model.setId(livreDto.getId());
        model.setIsbn(livreDto.getIsbn());
        model.setPrix(livreDto.getPrix());
        model.setDateCreation(livreDto.getDateCreation());
        model.setTitre(livreDto.getTitre());
        if (livreDto.getAuteurs() != null) {
            List<AuteurModel> auteursModel = livreDto.getAuteurs().stream()
                    .map(auteurMapper::toModel)
                    .collect(Collectors.toList());
            model.setAuteurs(auteursModel);
        }
        return model;
    }
}
