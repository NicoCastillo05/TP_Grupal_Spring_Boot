package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre listas_precio_articulos a la tabla.
@Table(name = "listas_precio_articulos")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListaPrecioArticulo extends AuditoriaApp {

    // Muchos registros de precios de articulos pueden usar la misma lista.
    // Si la lista es nueva, PERSIST permite guardarla tambien.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID de la lista de precios.
    @JoinColumn(name = "lista_precio_id", nullable = false)
    private ListaPrecio listaPrecio;

    // Guarda el precio de este articulo dentro de esta lista.
    // No permite null en la BD, pero no valida que el precio sea positivo.
    @Column(nullable = false)
    private double precioVenta;

    // Un articulo puede aparecer en distintas listas de precios.
    // Si el articulo es nuevo, PERSIST permite guardarlo tambien.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID del articulo.
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Une una lista, un articulo y su precio de venta.
    public ListaPrecioArticulo(ListaPrecio listaPrecio,
                               double precioVenta,
                               Articulo articulo,
                               Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.listaPrecio = listaPrecio;
        this.precioVenta = precioVenta;
        this.articulo = articulo;
    }
}