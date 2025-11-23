package com.comicverse.validation;
import com.comicverse.model.Comic;
import org.springframework.stereotype.Component;

@Component
public class ComicValidator {

    public void validarComic(Comic comic) {

        if (comic.getTitulo() == null || comic.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (comic.getDescripcion() == null || comic.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        if (comic.getEditorial() == null || comic.getEditorial().isBlank()) {
            throw new IllegalArgumentException("La editorial no puede estar vacía.");
        }

        if (comic.getAutor() == null || comic.getAutor().isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }

        if (comic.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        if (comic.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (comic.getImagen() == null || comic.getImagen().isBlank()) {
            throw new IllegalArgumentException("La imagen no puede estar vacía.");
        }
    }

}
