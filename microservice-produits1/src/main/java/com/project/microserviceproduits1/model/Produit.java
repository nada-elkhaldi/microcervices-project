package com.project.microserviceproduits1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titre;
    private String description;
    private String image;
    private Double prix;

    public Produit(){ }
    public Produit(String titre, String description, String image, Double prix) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Double getPrix() {
        return prix;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}
