package com.project.com.microservicecommandes.repository;

import com.project.com.microservicecommandes.model.Commande;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, String> {


    List<Commande> findByDateAfter(LocalDate date);
    Optional<Commande> findById(String id);
    void deleteById(String id);
    @Query("{ 'produit._id': ?0 }")
    List<Commande> findByProduitId(Integer produitId);
}
