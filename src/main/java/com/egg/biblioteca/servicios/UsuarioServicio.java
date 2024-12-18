package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombre, String email, String password, String password2, MultipartFile archivo) throws Exception {
        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);

        Imagen imagen = imagenServicio.guardarImagen(archivo);

        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);

    }

    public void validar(String nombre, String email, String password, String password2) throws Exception{

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = attr.getRequest().getSession(true);
            sesion.setAttribute("usuariosession", usuario);

            System.out.println(permisos);


            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);

            return user;
        }else{
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public void cambiarRol(UUID id) throws MiException {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);

        if(usuario.isPresent()){
            Usuario user = usuario.get();

            System.out.println("PRABANDO LOS DATOS: "+user.getRol().toString()+" "+user.getId());

            if(user.getRol().equals(Rol.USER)){
                user.setRol(Rol.ADMIN);
            }else{
                user.setRol(Rol.USER);
            }
            usuarioRepositorio.save(user);
        }else{
            throw new MiException("El usuario no existe");
        }
    }

    public Usuario getOne(UUID id) {
        Optional<Usuario> user = usuarioRepositorio.findById(id);
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }

    public void actualizar(MultipartFile archivo, UUID id, String nombre, String email, String password, String password2) throws Exception {

        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario user = respuesta.get();

            user.setNombre(nombre);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setEmail(email);
            user.setRol(Rol.USER);

            UUID idImagen = null;
            if(user.getImagen() != null){
                idImagen = user.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizarImagen(id,archivo );

            user.setImagen(imagen);

            usuarioRepositorio.save(user);
        }

    }
}
