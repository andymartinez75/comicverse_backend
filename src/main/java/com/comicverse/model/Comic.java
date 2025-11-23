package com.comicverse.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comics")

public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String editorial;
    private String autor;
    private double precio;
    private int stock;
    private boolean oferta;
    private String imagen;


}

