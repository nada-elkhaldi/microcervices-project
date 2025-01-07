package com.project.microserviceproduits1.service;


import com.project.microserviceproduits1.dto.ProduitDto;
import com.project.microserviceproduits1.exception.ValidationException;
import com.project.microserviceproduits1.model.Produit;
import com.project.microserviceproduits1.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RefreshScope
public class ProduitService {

    @Value("${mes-config-ms.produits-limit}")
    private int produitsLimit;

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public Produit createProduit(ProduitDto produitDTO) {
        if (produitDTO.getPrix() <= 0) {
            throw new ValidationException("Le prix doit être positif.");
        }

        Produit produit = new Produit(
                produitDTO.getTitre(),
                produitDTO.getDescription(),
                produitDTO.getImage(),
                produitDTO.getPrix()
        );
        return produitRepository.save(produit);
    }

    public Optional<Produit> getProduitById(Integer id) {
        return produitRepository.findById(id);
    }

    public Produit updateProduit(Integer id, ProduitDto produitDTO) {
        if (produitDTO.getPrix() <= 0) {
            throw new ValidationException("Le prix doit être positif.");
        }

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Produit non trouvé avec l'ID " + id));

        produit.setTitre(produitDTO.getTitre());
        produit.setDescription(produitDTO.getDescription());
        produit.setImage(produitDTO.getImage());
        produit.setPrix(produitDTO.getPrix());
        return produitRepository.save(produit);
    }

    public void deleteProduit(Integer id) {
        produitRepository.deleteById(id);
    }

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public List<Produit> getLimitedProduits() {
        return produitRepository.findAll().stream()
                .limit(produitsLimit)
                .collect(Collectors.toList());
    }

}

