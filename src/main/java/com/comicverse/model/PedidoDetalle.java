package com.comicverse.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PedidoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Un pedido puede tener muchos detalles
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Un comic puede aparecer en muchos pedidos
    @ManyToOne
    @JoinColumn(name = "comic_id")
    private Comic comic;

    private int cantidad;
    private double subtotal;

}
