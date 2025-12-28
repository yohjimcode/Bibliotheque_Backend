package Bibliotheque.service;

import Bibliotheque.rest.dto.AuteurDto;
import Bibliotheque.rest.dto.LivreDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LivreApiService {

    private final WebClient webClient;
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes";

    public LivreApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Récupère les informations d'un livre via son ISBN
     *
     * @param isbn l'ISBN du livre
     * @return LivreDto avec les informations récupérées, ou null si non trouvé
     */
    public LivreDto recupererInfosLivreParIsbn(String isbn) {
        try {
            GoogleBooksResponse response = webClient.get()
                    .uri(GOOGLE_BOOKS_API_URL + "?q=isbn:{isbn}", isbn)
                    .retrieve()
                    .bodyToMono(GoogleBooksResponse.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                GoogleBooksItem item = response.getItems().get(0);
                return mapperToLivreDto(item);
            }

        } catch (WebClientResponseException e) {
            log.error("Erreur lors de l'appel à Google Books API - ISBN: {}, Status: {}", isbn, e.getStatusCode());
        } catch (Exception e) {
            log.error("Erreur inattendue lors de la récupération des infos livre - ISBN: {}", isbn, e);
        }
        return null;
    }

    /**
     * Convertit une réponse Google Books en LivreDto
     */
    private LivreDto mapperToLivreDto(GoogleBooksItem item) {
        GoogleBooksVolumeInfo volumeInfo = item.getVolumeInfo();

        LivreDto dto = new LivreDto();
        dto.setTitre(volumeInfo.getTitle());
        dto.setIsbn(extractIsbn(volumeInfo.getIndustryIdentifiers()));

        // Récupérer tous les auteurs
        if (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) {
            List<AuteurDto> auteurs = volumeInfo.getAuthors().stream()
                    .map(this::parseAuteur)
                    .collect(Collectors.toList());
            dto.setAuteurs(auteurs);
        }

        return dto;
    }

    private AuteurDto parseAuteur(String nomComplet) {
        AuteurDto auteurDto = new AuteurDto();

        if (nomComplet != null && !nomComplet.trim().isEmpty()) {
            auteurDto.setNomAuteur(nomComplet.trim());
        }

        return auteurDto;
    }

    /**
     * Extrait l'ISBN_13 de la liste des identifiants
     */
    private String extractIsbn(List<GoogleBooksIdentifier> identifiers) {
        if (identifiers == null) {
            return null;
        }
        return identifiers.stream()
                .filter(id -> "ISBN_13".equals(id.getType()))
                .map(GoogleBooksIdentifier::getIdentifier)
                .findFirst()
                .orElse(null);
    }

    // Classes DTO pour la réponse Google Books API
    @Data
    @NoArgsConstructor
    static class GoogleBooksResponse {
        private List<GoogleBooksItem> items;
    }

    @Data
    @NoArgsConstructor
    static class GoogleBooksItem {
        private String id;
        private GoogleBooksVolumeInfo volumeInfo;
    }

    @Data
    @NoArgsConstructor
    static class GoogleBooksVolumeInfo {
        private String title;
        private List<String> authors;
        private String description;
        private String publisher;
        private String publishedDate;
        private List<GoogleBooksIdentifier> industryIdentifiers;
        private GoogleBooksImageLinks imageLinks;
    }

    @Data
    @NoArgsConstructor
    static class GoogleBooksIdentifier {
        private String type;
        private String identifier;
    }

    @Data
    @NoArgsConstructor
    static class GoogleBooksImageLinks {
        private String thumbnail;
    }
}

