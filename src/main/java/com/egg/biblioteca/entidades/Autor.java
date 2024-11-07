package com.egg.biblioteca.entidades;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_autor")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;


    public Autor(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
