package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, UUID id_autor, UUID id_editorial, String titulo, Integer ejemplares) throws MiException {

        validar(isbn, titulo, ejemplares, id_autor, id_editorial);

        Autor autorfind = autorRepositorio.findById(id_autor).get();
        Editorial editorial = editorialRepositorio.findById(id_editorial).get();
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setAlta(LocalDate.now());
        libro.setAutor(autorfind);
        libro.setEditorial(editorial);
        libro.setEjemplares(ejemplares);
        libro.setTitulo(titulo);

        libroRepositorio.save(libro);

    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();

        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, UUID id_autor, UUID id_editorial) throws MiException {

        System.out.println("VERIFICANDO DATOS"+isbn+titulo+ejemplares+id_autor+id_editorial);

        validar(isbn, titulo, ejemplares, id_autor, id_editorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setEjemplares(ejemplares);
            libro.setTitulo(titulo);
            libro.setAutor(autorRepositorio.findById(id_autor).get());
            libro.setEditorial(editorialRepositorio.findById(id_editorial).get());
            libroRepositorio.save(libro);
        }
    }

    public void validar(Long isbn, String titulo, Integer ejemplares, UUID id_autor, UUID id_editorial) throws MiException {

        if (isbn == null || isbn == 0) {
            throw new MiException("El ISBN no puede ser nulo");
        }
        if (ejemplares == null) {
            throw new MiException("La cantidad de ejemplares no puede ser nula");
        }
        if(id_autor == null){
            throw new MiException("El autor no puede ser nulo");
        }
        if(id_editorial == null){
            throw new MiException("La editorial no puede ser nula");
        }
        if(titulo.isEmpty() || titulo == null){
            throw new MiException("El titulo no puede ser vacio");
        }
    }

    @Transactional(readOnly = true)
    public Libro getOne(Long isbn){
        return libroRepositorio.getReferenceById(isbn);
    }



}
