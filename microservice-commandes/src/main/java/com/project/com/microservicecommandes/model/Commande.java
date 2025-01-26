package com.project.com.microservicecommandes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.com.microservicecommandes.feign.Produit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Document("commandes")
public class Commande {

    @Id
    private String id;
    private String description;
    private int quantite;
    private double montant;
    private LocalDate date;

    @JsonProperty("produit")
    private Produit produit;
    private Integer produitId;

    public Commande() {}
    public Commande(String description, int quantite, double montant, LocalDate date, Integer produitId) {
        this.description = description;
        this.quantite = quantite;
        this.montant = montant;
        this.date = date;
        this.produitId = produitId;

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Produit getProduit() {
        return produit;
    }
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getProduitId() {
        return produitId;
    }
    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }


}
