package com.project.com.microservicecommandes.configuration;

import com.project.com.microservicecommandes.feign.Produit;
import com.project.com.microservicecommandes.feign.ProxyProduit;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;

@Component
public class ProduitFeignFallback implements ProxyProduit {

    @Override
    public ResponseEntity<Produit> getProduitById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Produit>> getProduits() {
        System.out.println("Fallback déclenché : Service Produits indisponible");

       
        Produit produitPlaceholder = new Produit();
        produitPlaceholder.setDescription("Produit indisponible");
        List<Produit> produitsFallback = Collections.singletonList(produitPlaceholder);

        return ResponseEntity.ok(produitsFallback);
    }


    @Override
    public String healthCheck() {
        return "";
    }
}

