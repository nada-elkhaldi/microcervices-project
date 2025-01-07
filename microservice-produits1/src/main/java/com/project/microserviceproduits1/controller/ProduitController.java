package com.project.microserviceproduits1.controller;


import com.project.microserviceproduits1.dto.ProduitDto;
import com.project.microserviceproduits1.exception.ValidationException;
import com.project.microserviceproduits1.model.Produit;
import com.project.microserviceproduits1.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ms-produits", method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping("/create-produit")
    public ResponseEntity<Produit> createProduit(@RequestBody ProduitDto produitDto) {
        try {
            Produit createdProduit = produitService.createProduit(produitDto);
            return ResponseEntity.ok(createdProduit);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/produit/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Integer id) {
        return produitService.getProduitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update-produit/{id}")
    public ResponseEntity<Produit> updateProduit(
            @PathVariable Integer id,
            @RequestBody ProduitDto produitDto) {
        try {
            Produit updatedProduit = produitService.updateProduit(id, produitDto);
            return ResponseEntity.ok(updatedProduit);
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete-produit/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Integer id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getProduits() throws InterruptedException {
        List<Produit> produits = produitService.getLimitedProduits();
        // Simuler un délai de 5 secondes
        Thread.sleep(5000);

        List<Produit> produits1 = new ArrayList<>();
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/produits-limite")
    public ResponseEntity<List<Produit>> getLimitedProduits() {
        //List<Produit> produits = produitService.getLimitedProduits();
        List<Produit> produits = produitService.getAllProduits();
        return ResponseEntity.ok(produits);
    }


    public class HealthController {

        @GetMapping("/health")
        public ResponseEntity<String> healthCheck() {
            return ResponseEntity.ok("UP");
        }
    }
}
