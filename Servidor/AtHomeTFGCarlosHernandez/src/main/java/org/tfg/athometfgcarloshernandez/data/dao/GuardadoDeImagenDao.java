package org.tfg.athometfgcarloshernandez.data.dao;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tfg.athometfgcarloshernandez.domain.errores.CustomedException;
import org.tfg.athometfgcarloshernandez.domain.errores.DatabaseException;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesError;
import org.tfg.athometfgcarloshernandez.spring.common.constantes.ConstantesSpring;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class GuardadoDeImagenDao {
    @Value(ConstantesSpring.IMAGE_STORE_PATH)
    private String imageStorePath;

    private final Path directorioUploads = Paths.get(imageStorePath);

    public String guardarImagen(MultipartFile imagen) {
        try {
            Files.createDirectories(directorioUploads);
            String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path rutaArchivo = directorioUploads.resolve(nombreArchivo);
            Files.copy(imagen.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
            return nombreArchivo;
        } catch (IOException e) {
            throw new DatabaseException(ConstantesError.ERROR_BASE_DATOS);
        }
    }
    public Resource cargarImagen(String nombre) {
            Path imagePath = directorioUploads.resolve(nombre).normalize();
            try {
                Resource resource = new UrlResource(imagePath.toUri());
                if (!resource.exists()) {
                    throw new CustomedException(ConstantesError.IMAGEN_NO_ENCONTRADA);
                }
                return resource;
            } catch (IOException e) {
                throw new CustomedException(ConstantesError.ERROR_BASE_DATOS);
            }
    }

    public void eliminarImagen(String nombre) {
        try {
            Path imagePath = directorioUploads.resolve(nombre).normalize();
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new CustomedException(ConstantesError.ERROR_BASE_DATOS);
        }
    }
}


