package Bibliotheque.service.mapper;

import Bibliotheque.infra.model.UtilisateurModel;
import Bibliotheque.rest.dto.UtilisateurDto;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public UtilisateurDto toDto(UtilisateurModel utilisateurModel){
        if(utilisateurModel == null){
            return null;
        }
        UtilisateurDto dto = new UtilisateurDto();
        dto.setId(utilisateurModel.getId());
        dto.setMail(utilisateurModel.getMail());
        dto.setUsername(utilisateurModel.getUsername());
        dto.setPassword(utilisateurModel.getPassword());
        dto.setDateCreation(utilisateurModel.getDateCreation());
        dto.setDateSuppresion(utilisateurModel.getDateSuppresion());
        return dto;
    }

    public UtilisateurModel toModel(UtilisateurDto utilisateurDto){
        if(utilisateurDto == null){
            return null;
        }
        UtilisateurModel model = new UtilisateurModel();
        model.setId(utilisateurDto.getId());
        model.setMail(utilisateurDto.getMail());
        model.setUsername(utilisateurDto.getUsername());
        model.setPassword(utilisateurDto.getPassword());
        model.setDateCreation(utilisateurDto.getDateCreation());
        model.setDateSuppresion(utilisateurDto.getDateSuppresion());
        return model;
    }
}
