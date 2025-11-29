package com.comicverse.service;

import com.comicverse.model.Comic;
import com.comicverse.model.Pedido;
import com.comicverse.model.PedidoDetalle;
import com.comicverse.validation.PedidoValidator;
import com.comicverse.repository.ComicRepository;
import com.comicverse.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private PedidoValidator pedidoValidator;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El pedido con ID " + id + " no existe"));
    }

    public Pedido crearPedido(Pedido pedido) {

        // Validación general
        pedidoValidator.validarPedido(pedido);

        // Fecha automática
        pedido.setFecha(LocalDate.now());

        // Procesar detalles (comics, stock, subtotales)
        double total = procesarDetalles(pedido);
        pedido.setTotal(total);

        // Guardar el pedido completo
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarPedido(Long id, Pedido pedido) {

        Pedido existente = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el pedido con ID " + id));

        // Validamos pedido
        pedidoValidator.validarPedido(pedido);

        // Sobreescribimos datos
        existente.setUsuario(pedido.getUsuario());
        existente.setFecha(LocalDate.now());

        // Procesar nuevamente detalles
        double total = procesarDetalles(pedido);
        existente.setTotal(total);

        // Guardar
        return pedidoRepository.save(existente);
    }

    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se puede eliminar: el pedido con ID " + id + " no existe");
        }
        pedidoRepository.deleteById(id);
    }

    // ============================================
    //  MÉTODO PRIVADO QUE PROCESA LOS DETALLES
    // ============================================

    private double procesarDetalles(Pedido pedido) {
        double total = 0;

        for (PedidoDetalle detalle : pedido.getDetalles()) {

            // Buscar comic real
            Comic comic = comicRepository.findById(detalle.getComic().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Comic con ID " + detalle.getComic().getId() + " no encontrado"));

            // Asignamos comic real
            detalle.setComic(comic);

            // Calcular subtotal
            double subtotal = comic.getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);

            // Sumar total
            total += subtotal;

            // Vincular detalle → pedido
            detalle.setPedido(pedido);

            // Actualizar stock
            if (comic.getStock() < detalle.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el comic: " + comic.getTitulo());
            }

            comic.setStock(comic.getStock() - detalle.getCantidad());
        }

        return total;
    }
}




