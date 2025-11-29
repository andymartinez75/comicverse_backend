package com.comicverse.controller;

import com.comicverse.model.Comic;
import com.comicverse.service.ComicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ComicController {

    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Comic> comics = comicService.obtenerTodos();
            return ResponseEntity.ok(comics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de cómics");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {

        try {
            var optionalComic = comicService.obtenerPorId(id);

            if (optionalComic.isPresent()) {
                return ResponseEntity.ok(optionalComic.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cómic no encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el cómic");
        }
    }

    @PostMapping
    public ResponseEntity<?> crearComic(@RequestBody Comic comic) {
        try {
            Comic creado = comicService.guardar(comic);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el cómic");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Comic comicActualizado) {
        try {
            if (!comicService.existe(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cómic no existe");
            }

            comicActualizado.setId(id);
            Comic actualizado = comicService.actualizar(id, comicActualizado);
            return ResponseEntity.ok(actualizado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el cómic");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            if (!comicService.existe(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cómic no existe");
            }

            comicService.eliminar(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el cómic");
        }
    }
}
