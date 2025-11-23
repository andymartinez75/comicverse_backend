package com.comicverse.controller;
import com.comicverse.model.Comic;
import com.comicverse.service.ComicService;
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

    // GET - listar todos
    @GetMapping
    public List<Comic> obtenerTodos() {
        return comicService.obtenerTodos();
    }

    // GET - buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comic> obtenerPorId(@PathVariable Long id) {
        return comicService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - crear nuevo comic
    @PostMapping
    public Comic crearComic(@RequestBody Comic comic) {
        return comicService.guardar(comic);
    }

    // PUT - actualizar comic
    @PutMapping("/{id}")
    public ResponseEntity<Comic> actualizar(@PathVariable Long id, @RequestBody Comic comicActualizado) {

        if (!comicService.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        comicActualizado.setId(id);
        return ResponseEntity.ok(comicService.actualizar(id, comicActualizado));
    }

    // DELETE - eliminar comic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!comicService.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        comicService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
