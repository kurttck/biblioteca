package com.egg.biblioteca.repositorios;

import com.egg.biblioteca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("select u from Usuario u where u.email = :email")
    public Usuario buscarPorEmail(String email);




}
