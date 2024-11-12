package com.egg.biblioteca.entidades;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="imagenes")
public class Imagen {
    @Id
    @Column(name="id_imagen")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nombre;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;


    public Imagen(){

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

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
}
