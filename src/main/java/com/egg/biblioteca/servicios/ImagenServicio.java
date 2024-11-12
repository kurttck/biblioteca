package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagenServicio {

    @Autowired
    ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardarImagen(MultipartFile archivo){
        if(archivo != null){

            try{
                Imagen img = new Imagen();

                img.setNombre(archivo.getName());
                img.setContenido(archivo.getBytes());



                return imagenRepositorio.save(img);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }

        return null;
    }


    @Transactional
    public Imagen actualizarImagen(UUID id, MultipartFile archivo) throws MiException {
        if(archivo!=null){

            try{
                Imagen imagen = new Imagen();
                if(id !=null){
                    Optional<Imagen> img = imagenRepositorio.findById(String.valueOf(id));

                    if(img.isPresent()){
                        imagen = img.get();
                    }
                }
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
