package Bibliotheque.service;

import Bibliotheque.infra.model.UtilisateurModel;
import Bibliotheque.infra.repo.UserJpaRepository;
import Bibliotheque.rest.dto.UtilisateurDto;
import Bibliotheque.service.mapper.UtilisateurMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder, UtilisateurMapper utilisateurMapper) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurMapper = utilisateurMapper;
    }

    public UtilisateurDto register(UtilisateurDto dto) {
        if (userJpaRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("userName déja existant");
        }
        UtilisateurModel user = utilisateurMapper.toModel(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UtilisateurModel userSave = userJpaRepository.save(user);
        return utilisateurMapper.toDto(userSave);
    }
}
