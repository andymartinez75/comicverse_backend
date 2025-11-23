package com.comicverse.service;
import com.comicverse.model.Comic;
import com.comicverse.repository.ComicRepository;
import com.comicverse.validation.ComicValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComicService {
    private final ComicRepository comicRepository;
    private final ComicValidator validator;

    public ComicService(ComicRepository comicRepository, ComicValidator validator) {
        this.comicRepository = comicRepository;
        this.validator = validator;
    }
    // GET - obtener todos
    public List<Comic> obtenerTodos() {
        return comicRepository.findAll();
    }

    // GET - obtener por ID
    public Optional<Comic> obtenerPorId(Long id) {
        return comicRepository.findById(id);
    }

    // POST - guardar nuevo
    public Comic guardar(Comic comic) {

        // 🔍 Validaciones previas
        validator.validarComic(comic);

        // 🔍 Validar duplicados
        if (comicRepository.existsByTituloAndEditorial(comic.getTitulo(), comic.getEditorial())) {
            throw new IllegalArgumentException("Ya existe un comic con este título y esta editorial.");
        }

        return comicRepository.save(comic);
    }

    // PUT - actualizar
    public Comic actualizar(Long id, Comic comicActualizado) {

        if (!comicRepository.existsById(id)) {
            throw new IllegalArgumentException("El comic con ID " + id + " no existe.");
        }

        validator.validarComic(comicActualizado);

        // Evitar duplicado en actualización:
        // Solo si el título+editorial pertenecen a OTRO comic
        Optional<Comic> existente = comicRepository.findById(id);
        if (existente.isPresent()) {
            Comic original = existente.get();

            boolean cambiaTitulo = !original.getTitulo().equals(comicActualizado.getTitulo());
            boolean cambiaEditorial = !original.getEditorial().equals(comicActualizado.getEditorial());

            if ((cambiaTitulo || cambiaEditorial) &&
                    comicRepository.existsByTituloAndEditorial(comicActualizado.getTitulo(), comicActualizado.getEditorial())) {

                throw new IllegalArgumentException("No se puede actualizar: ya existe otro comic con ese título y editorial.");
            }
        }

        // Forzar ID correcto
        comicActualizado.setId(id);

        return comicRepository.save(comicActualizado);
    }

    // DELETE: eliminar por ID
    public void eliminar(Long id) {

        if (!comicRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el comic con ID " + id + " no existe.");
        }

        comicRepository.deleteById(id);
    }

    // Comprobar existencia por ID
    public boolean existe(Long id) {
        return comicRepository.existsById(id);
    }



}
