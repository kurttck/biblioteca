package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        System.out.println("HOLA VERIFICANDO");

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer ejemplares,
                           @RequestParam(required = false) UUID idAutor, @RequestParam(required = false) UUID idEditorial, ModelMap modelo){

        try{

            libroServicio.crearLibro(isbn, idAutor, idEditorial, titulo, ejemplares);
            modelo.put("exito", "Se ha registrado el libro correctamente");

        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "libro_form.html"; // volvemos a cargar el formulario.
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo){
        Libro libro = libroServicio.getOne(isbn);
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.put("libro", libro);
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificador.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, @RequestParam String titulo, @RequestParam Integer ejemplares,
                            @RequestParam UUID idAutor, @RequestParam UUID idEditorial, ModelMap modelo){
        System.out.println("isbn:"+isbn);
        try{

           libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:/libro/lista";
        }catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "libro_modificador.html";
        }
    }


}
