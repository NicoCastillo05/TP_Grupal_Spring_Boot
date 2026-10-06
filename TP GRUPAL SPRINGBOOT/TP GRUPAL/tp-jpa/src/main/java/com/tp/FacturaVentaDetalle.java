package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre facturas_venta_detalles a la tabla.
@Table(name = "facturas_venta_detalles")
// Hereda el ID. Esta clase no hereda los campos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"factura"})
@ToString(callSuper = true, exclude = {"factura"})
public class FacturaVentaDetalle extends EntityId {

    // Muchos detalles pueden pertenecer a una misma factura.
    @ManyToOne
    // Guarda obligatoriamente el ID de la factura.
    // Esta es la clave foranea que relaciona el detalle con su cabecera.
    @JoinColumn(name = "factura_id", nullable = false)
    private FacturaVenta factura;

    // Muchos detalles pueden usar el mismo articulo de una lista de precios.
    // Si ListaPrecioArticulo es nuevo, PERSIST permite guardarlo tambien.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID de ListaPrecioArticulo.
    @JoinColumn(name = "lista_precio_articulo_id", nullable = false)
    private ListaPrecioArticulo listaPrecioArticulo;

    // La descripcion puede quedar en null.
    private String descripcion;

    // La cantidad no puede almacenarse como null.
    @Column(nullable = false)
    private double cantidad;

    // El precio unitario no puede almacenarse como null.
    @Column(nullable = false)
    private double precioUnitario;

    private double porcentajeBonificacion;
    private double importeNeto;
    private double importeIva;

    // El subtotal no puede almacenarse como null.
    @Column(nullable = false)
    private double importeSubtotal;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Recibe los datos e importes del detalle, sin calcularlos.
    // La factura se asigna despues mediante factura.agregarDetalle(...).
    public FacturaVentaDetalle(ListaPrecioArticulo listaPrecioArticulo,
                               String descripcion,
                               double cantidad,
                               double precioUnitario,
                               double porcentajeBonificacion,
                               double importeNeto,
                               double importeIva,
                               double importeSubtotal) {

        this.listaPrecioArticulo = listaPrecioArticulo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.porcentajeBonificacion = porcentajeBonificacion;
        this.importeNeto = importeNeto;
        this.importeIva = importeIva;
        this.importeSubtotal = importeSubtotal;
    }

    // Asigna la factura a la que pertenece.
    // Al no tener public, se puede llamar desde clases del mismo paquete,
    // como FacturaVenta.
    void setFactura(FacturaVenta factura) {
        this.factura = factura;
    }
}