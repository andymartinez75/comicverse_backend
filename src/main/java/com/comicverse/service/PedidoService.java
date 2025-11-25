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
        return pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "El pedido con ID " + id + " no existe"));
    }

    public Pedido crearPedido(Pedido pedido) {

        pedidoValidator.validarPedido(pedido);

        // Fecha automática
        pedido.setFecha(LocalDate.now());

        double total = 0;

        // Proceso cada detalle
        for (PedidoDetalle detalle : pedido.getDetalles()) {

            // Buscar comic real en la BD
            Comic comicReal = comicRepository.findById(detalle.getComic().getId())
                    .orElseThrow(() -> new RuntimeException("Comic no encontrado"));

            // Asigno el comic completo al detalle
            detalle.setComic(comicReal);

            // Calculo subtotal
            double subtotal = comicReal.getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);

            // Sumo al total del pedido
            total += subtotal;

            // Relación bidireccional: DETALLE → PEDIDO
            detalle.setPedido(pedido);

            // Reducir stock
            comicReal.setStock(comicReal.getStock() - detalle.getCantidad());
        }

        //Guardar total en Pedido
        pedido.setTotal(total);

        //Guardar pedido completo con detalles
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarPedido(Long id, Pedido pedido) {

        Pedido existente = pedidoRepository.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setUsuario(pedido.getUsuario());
        existente.setTotal(pedido.getTotal());
        existente.setDetalles(pedido.getDetalles());

        return pedidoRepository.save(existente);
    }

    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}



