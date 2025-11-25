package com.comicverse.validation;
import com.comicverse.model.Comic;
import com.comicverse.model.Pedido;
import com.comicverse.model.PedidoDetalle;
import com.comicverse.repository.ComicRepository;
import org.springframework.stereotype.Component;

@Component
public class PedidoValidator {

    private final ComicRepository comicRepository;

    public PedidoValidator(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    public void validarPedido(Pedido pedido) {

        // 1) Usuario
        if (pedido.getUsuario() == null || pedido.getUsuario().isBlank()) {
            throw new IllegalArgumentException("El usuario es obligatorio para realizar un pedido");
        }

        // 2) Detalles no vacíos
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("El pedido no puede estar vacío");
        }

        // 3) Validar cada detalle
        for (PedidoDetalle detalle : pedido.getDetalles()) {

            if (detalle.getComic() == null) {
                throw new IllegalArgumentException("Cada detalle debe tener un comic asignado");
            }

            Comic comic = comicRepository.findById(detalle.getComic().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El comic con ID " + detalle.getComic().getId() + " no existe"));

            // 4) Cantidad válida
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
            }

            // 5) Stock
            if (comic.getStock() < detalle.getCantidad()) {
                throw new IllegalArgumentException(
                        "No hay stock suficiente para el comic: " + comic.getTitulo());
            }
        }
    }
}

