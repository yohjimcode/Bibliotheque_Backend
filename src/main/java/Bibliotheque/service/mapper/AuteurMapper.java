package Bibliotheque.service.mapper;

import Bibliotheque.infra.model.AuteurModel;
import Bibliotheque.rest.dto.AuteurDto;
import org.springframework.stereotype.Component;

@Component
public class AuteurMapper {

    public AuteurDto toDto(AuteurModel auteurModel) {
        if (auteurModel == null) {
            return null;
        }
        AuteurDto dto = new AuteurDto();
        dto.setId(auteurModel.getId());
        dto.setNomAuteur(auteurModel.getNomAuteur());
        return dto;
    }

    public AuteurModel toModel(AuteurDto auteurDto) {
        if (auteurDto == null) {
            return null;
        }
        AuteurModel model = new AuteurModel();
        model.setId(auteurDto.getId());
        model.setNomAuteur(auteurDto.getNomAuteur());
        return model;
    }
}
