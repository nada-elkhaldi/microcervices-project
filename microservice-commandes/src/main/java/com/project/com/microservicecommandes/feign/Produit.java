package com.project.com.microservicecommandes.feign;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Produit {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("titre")
    private String titre;
    @JsonProperty("description")
    private String description;
    @JsonProperty("image")
    private String image;
    @JsonProperty("prix")
    private Double prix;
}
