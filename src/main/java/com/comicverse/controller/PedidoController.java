package com.comicverse.controller;

import com.comicverse.dto.PedidoDTO;
import com.comicverse.mapper.PedidoMapper;
import com.comicverse.model.Pedido;
import com.comicverse.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @GetMapping
    public List<PedidoDTO> listarPedidos() {
        return pedidoService.listarPedidos()
                .stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PedidoDTO obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPedido(id);
        return pedidoMapper.toDTO(pedido);
    }

    @PostMapping
    public PedidoDTO crearPedido(@RequestBody Pedido pedido) {
        Pedido creado = pedidoService.crearPedido(pedido);
        return pedidoMapper.toDTO(creado);
    }

    @PutMapping("/{id}")
    public PedidoDTO actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido actualizado = pedidoService.actualizarPedido(id, pedido);
        return pedidoMapper.toDTO(actualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
    }
}

