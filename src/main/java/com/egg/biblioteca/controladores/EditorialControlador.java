package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registrado(String nombreEditorial){

        try{
            editorialServicio.crearEditorial(nombreEditorial);

        }catch (MiException e){
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, e);
            return "editorial_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }

    @GetMapping("modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable UUID id){
        modelo.addAttribute("editorial", editorialServicio.getOne(id));
        return "editorial_modificador.html";
    }

    @PostMapping("modificar/{id}")
    public String modificar(@PathVariable UUID id, String nombre, ModelMap modelo){
        try{
            editorialServicio.modificarEditorial(nombre, id);
            return  "redirect:../lista";

        }catch (MiException e){
            modelo.put("error", e.getMessage());
            return "editorial_modificador.html";
        }
    }
}
