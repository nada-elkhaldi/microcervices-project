package com.project.com.microservicecommandes.controller;

import com.project.com.microservicecommandes.dto.CommandeDto;
import com.project.com.microservicecommandes.exception.ValidationException;
import com.project.com.microservicecommandes.model.Commande;
import com.project.com.microservicecommandes.service.CommandeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value= "/api/ms-commandes",  method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
public class CommandeController implements HealthIndicator {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping("/create-commande")
    public ResponseEntity<Commande> createCommande(@RequestBody CommandeDto commandeDto) {
        try {
            Commande createdCommande = commandeService.createCommande(commandeDto);
            return ResponseEntity.ok(createdCommande);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/commande/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable String id) {
        return commandeService.getCommandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/commandes-par-produit/{produitId}")
    @CircuitBreaker(name = "commande-service", fallbackMethod = "fallbackGetAllCommandes")
    public ResponseEntity<List<Commande>> getCommandesByProduitId(@PathVariable Integer produitId) {
        List<Commande> commandes = commandeService.getCommandesByProduitId(produitId);
        return ResponseEntity.ok(commandes);
    }
    @PutMapping("/update-commande/{id}")
    public ResponseEntity<Commande> updateCommande(
            @PathVariable String id,
            @RequestBody CommandeDto commandeDto) {
        try {
            Commande updatedCommande = commandeService.updateCommande(id, commandeDto);
            return ResponseEntity.ok(updatedCommande);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete-commande/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable String id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    @CircuitBreaker(name = "commande-service", fallbackMethod = "fallbackGetAllCommandes")
//    public ResponseEntity<List<Commande>> getAllCommandes() {
//        List<Commande> commandes = commandeService.getAllCommandes();
//        return ResponseEntity.ok(commandes);
//    }



    @GetMapping("/commandes-recentes")

    public ResponseEntity<List<Commande>> getDernieresCommandes() {
        List<Commande> commandes = commandeService.getDernieresCommandes();
        return ResponseEntity.ok(commandes);
    }

    @Override
    public Health health() {
        List<Commande> commandes = commandeService.getAllCommandes();
        if (commandes.isEmpty()) {
            return Health.down().build();
        } else {
            return Health.up().build();
        }
    }

    @GetMapping
    @TimeLimiter(name = "commandesService", fallbackMethod = "fallbackGetAllCommandes")
    @CircuitBreaker(name = "commandesService", fallbackMethod = "fallbackGetAllCommandes")
    public CompletableFuture<List<Commande>> getAllCommandes() {
        return CompletableFuture.supplyAsync(() -> commandeService.getAllCommandes());
    }

    @GetMapping("/error")
    public CompletableFuture<List<Commande>> fallbackGetAllCommandes(Throwable throwable) {
        System.out.println("Fallback triggered: " + throwable.getMessage());

        List<Commande> fallbackResponse = new ArrayList<>();
        Commande placeholderCommande = new Commande();
        placeholderCommande.setDescription("Service Produits indisponible. Impossible de récupérer les commandes.");
        fallbackResponse.add(placeholderCommande);

        return CompletableFuture.completedFuture(fallbackResponse);
    }


//    @GetMapping
//    @TimeLimiter(name = "commandesService", fallbackMethod = "fallbackGetAllCommandes")
//    @CircuitBreaker(name = "commandesService", fallbackMethod = "fallbackGetAllCommandes")
//    public CompletableFuture<List<Commande>> getAllCommandes() {
//        return CompletableFuture.supplyAsync(() -> commandeService.getAllCommandes());
//    }
//
//
//    public CompletableFuture<List<Commande>> fallbackGetAllCommandes(Throwable throwable) {
//        System.out.println("Fallback déclenché : " + throwable.getMessage());
//        Commande placeholderCommande = new Commande();
//        placeholderCommande.setDescription("Service Produits indisponible. Impossible de récupérer les commandes.");
//        return CompletableFuture.completedFuture(Collections.singletonList(placeholderCommande));
//    }

}




