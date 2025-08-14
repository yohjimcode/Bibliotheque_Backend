package Bibliotheque.service.mapper;

import Bibliotheque.infra.model.AuteurModel;
import Bibliotheque.rest.dto.AuteurDto;
import org.springframework.stereotype.Component;

@Component
public class AuteurMapper {

    public AuteurDto toDto(AuteurModel auteurModel){
        if(auteurModel == null){
            return null;
        }
        AuteurDto dto = new AuteurDto();
        dto.setId(auteurModel.getId());
        dto.setNom(auteurModel.getNom());
        dto.setLivres(auteurModel.getLivres());
        dto.setPrenom(auteurModel.getPrenom());
        dto.setBiographie(auteurModel.getBiographie());
        return dto;
    }

    public  AuteurModel toModel(AuteurDto auteurDto){
        if(auteurDto == null){
            return null;
        }
        AuteurModel model = new AuteurModel();
        model.setId(auteurDto.getId());
        model.setNom(auteurDto.getNom());
        model.setPrenom(auteurDto.getPrenom());
        model.setBiographie(auteurDto.getBiographie());
        model.setLivres(auteurDto.getLivres());
        return model;
    }
}
