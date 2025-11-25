package com.comicverse.dto;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data

public class PedidoDTO {
    private Long id;
    private String usuario;
    private LocalDate fecha;
    private double total;
    private List<PedidoDetalleDTO> detalles;
}
