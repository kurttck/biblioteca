package com.egg.biblioteca.entidades;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "editoriales")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_editorial")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    public Editorial(){

    }

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
