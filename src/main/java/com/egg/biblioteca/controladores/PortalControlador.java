package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/") //Este es el mapeo
    public String index(){
        return "index.html";
    }

    @GetMapping("/registrar")
    public String registro(){
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,@RequestParam String email,@RequestParam String password, String password2, ModelMap modelo){

        try{
            usuarioServicio.registrar(nombre, email, password, password2);

            modelo.put("exito", "Usuario registrado correctamente");

            return  "index.html";
        }catch (MiException e){
            modelo.put("error", e.getMessage());

            return "registro.html";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        if(error != null){
            modelo.put("error", "Usuario o contraseña incorrectos");
        }

        return "login.html";
    }


    @PreAuthorize("hasAnyRole('ROLE_USUARIO', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }





}
