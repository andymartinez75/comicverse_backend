package com.comicverse.dto;
import lombok.Data;

@Data
public class PedidoDetalleDTO {
    private Long id;
    private int cantidad;
    private double subtotal;
    private ComicDTO comic;
}
