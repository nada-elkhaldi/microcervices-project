package com.project.com.microservicecommandes.feign;


import com.project.com.microservicecommandes.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MICROSERVICE-PRODUITS1", url = "http://localhost:8081/api/ms-produits",  configuration = FeignConfig.class)
public interface ProxyProduit {
    @GetMapping("/produit/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Integer id);
    @GetMapping
    public ResponseEntity<List<Produit>> getProduits();

    @GetMapping("/health")
    String healthCheck();
}