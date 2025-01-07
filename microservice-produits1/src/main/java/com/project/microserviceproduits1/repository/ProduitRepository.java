package com.project.microserviceproduits1.repository;

import com.project.microserviceproduits1.model.Produit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProduitRepository extends CrudRepository<Produit, Integer> {

    List<Produit> findAll();


}
