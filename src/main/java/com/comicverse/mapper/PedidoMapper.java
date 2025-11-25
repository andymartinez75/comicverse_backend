package com.comicverse.mapper;
import com.comicverse.dto.*;
import com.comicverse.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component

public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setUsuario(pedido.getUsuario());
        dto.setFecha(pedido.getFecha());
        dto.setTotal(pedido.getTotal());

        dto.setDetalles(
                pedido.getDetalles()
                        .stream()
                        .map(this::toDetalleDTO)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public PedidoDetalleDTO toDetalleDTO(PedidoDetalle detalle) {
        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setComic(toComicDTO(detalle.getComic()));
        return dto;
    }

    public ComicDTO toComicDTO(Comic comic) {
        ComicDTO dto = new ComicDTO();
        dto.setId(comic.getId());
        dto.setTitulo(comic.getTitulo());
        dto.setPrecio(comic.getPrecio());
        dto.setImagen(comic.getImagen());
        return dto;
    }
}
