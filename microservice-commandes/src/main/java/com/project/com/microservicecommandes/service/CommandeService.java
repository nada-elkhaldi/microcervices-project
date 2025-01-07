package com.project.com.microservicecommandes.service;

import com.project.com.microservicecommandes.dto.CommandeDto;
import com.project.com.microservicecommandes.exception.ValidationException;
import com.project.com.microservicecommandes.feign.Produit;
import com.project.com.microservicecommandes.feign.ProxyProduit;
import com.project.com.microservicecommandes.model.Commande;
import com.project.com.microservicecommandes.repository.CommandeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RefreshScope
@Service
public class CommandeService {

    private final ProxyProduit proxyProduit;
    @Value("${mes-config-ms.commandes-last}")
    private int commandesLast;

    private final CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository, ProxyProduit proxyProduit) {
        this.commandeRepository = commandeRepository;
        this.proxyProduit = proxyProduit;
    }

    public Commande createCommande(CommandeDto commandeDTO) {
        if (commandeDTO.getQuantite() <= 0) {
            throw new ValidationException("La quantité doit être positive.");
        }

        //produit
        Produit produit = proxyProduit.getProduitById(commandeDTO.getIdProduit()).getBody();
        if (produit == null) {
            throw new ValidationException("Produit non trouvé avec l'ID : " + commandeDTO.getIdProduit());
        }
        Commande commande = new Commande(
                commandeDTO.getDescription(),
                commandeDTO.getQuantite(),
                commandeDTO.getMontant(),
                commandeDTO.getDate()
        );
        commande.setProduit(produit);
        return commandeRepository.save(commande);
    }

    public List<Commande> getCommandesByProduitId(Integer produitId) {
        System.out.println("Produit ID reçu : " + produitId);
        List<Commande> commandes = new ArrayList<>();

        try {
            proxyProduit.getProduitById(produitId);
            commandes = commandeRepository.findByProduitId(produitId);
            System.out.println("Commandes trouvées : " + commandes.size());
        } catch (RuntimeException e) {
            System.out.println("Erreur lors de l'appel au microservice Produits : " + e.getMessage());
        }
        return commandes;
    }

    public Optional<Commande> getCommandeById(String id) {
        return commandeRepository.findById(id);
    }

    public Commande updateCommande(String id, CommandeDto commandeDTO) {
        if (commandeDTO.getQuantite() <= 0) {
            throw new ValidationException("La quantité doit être positive.");
        }
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Commande non trouvée avec l'ID \" + id"));

        commande.setDescription(commandeDTO.getDescription());
        commande.setQuantite(commandeDTO.getQuantite());
        commande.setMontant(commandeDTO.getMontant());
        commande.setDate(commandeDTO.getDate());
        return commandeRepository.save(commande);
    }


    public void deleteCommande(String id) {
        commandeRepository.deleteById(id);
    }


//    public List<Commande> getAllCommandes() {
//        // Liste des commandes
//        List<Commande> commandesValides = new ArrayList<>();
//        // Récupérer les produits via proxy
//        ResponseEntity<List<Produit>> response = proxyProduit.getProduits();
//        if (response.getStatusCode().is2xxSuccessful()) {
//            List<Produit> produits = response.getBody();
//
//            // Si les produits sont accessibles, on récupère toutes les commandes
//            commandesValides = commandeRepository.findAll();
//        } else {
//            throw new RuntimeException("Erreur lors de la récupération des produits depuis le microservice. Code de statut : " + response.getStatusCode());
//        }
//        return commandesValides;
//    }

    public List<Commande> getAllCommandes() {
        ResponseEntity<List<Produit>> response = proxyProduit.getProduits();
        return commandeRepository.findAll();
    }

    public List<Commande> getDernieresCommandes() {
        LocalDate dateLimite = LocalDate.now().minusDays(commandesLast);
        ResponseEntity<List<Produit>> response = proxyProduit.getProduits();
        if (response.getStatusCode().is2xxSuccessful()) {
            return commandeRepository.findByDateAfter(dateLimite);
        } else {
            throw new RuntimeException("Erreur lors de la récupération des produits avec statut : " + response.getStatusCode());
        }
    }

//    // methode pour recuperer les dernieres commandes
//    public List<Commande> getDernieresCommandes() {
//        LocalDate dateLimite = LocalDate.now().minusDays(commandesLast);
//        List<Commande> commandesValides = new ArrayList<>();
//
//        try {
//            ResponseEntity<List<Produit>> response = proxyProduit.getProduits();
//            if (response.getStatusCode().is2xxSuccessful()) {
//                List<Produit> produits = response.getBody();
//
//                // si les produits sont accessibles, on recupere toutes les commandes
//                commandesValides = commandeRepository.findByDateAfter(dateLimite);
//            } else {
//                System.out.println("Erreur lors de la récupération des produits depuis le microservice. Code de statut : " + response.getStatusCode());
//            }
//        } catch (RuntimeException e) {
//            System.out.println("Erreur lors de l'appel au microservice Produits : " + e.getMessage());
//        }
//        return commandesValides;
//    }
//public List<Commande> getDernieresCommandes() {
//    LocalDate dateLimite = LocalDate.now().minusDays(commandesLast);
//    return commandeRepository.findByDateAfter(dateLimite);
//}
}
