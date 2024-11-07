package com.egg.biblioteca.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @Column(name = "isbn")
    private Long isbn;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "ejemplares")
    private Integer ejemplares;

    @Column(name = "alta")
    private LocalDate alta;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    public Libro(){

    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getAlta() {
        return alta;
    }

    public void setAlta(LocalDate alta) {
        this.alta = alta;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}



