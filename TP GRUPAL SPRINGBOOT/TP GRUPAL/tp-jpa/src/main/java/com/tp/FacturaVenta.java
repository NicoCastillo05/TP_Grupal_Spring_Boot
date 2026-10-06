package com.tp;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre facturas_venta a la tabla.
@Table(name = "facturas_venta")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"detalles"})
@ToString(callSuper = true, exclude = {"detalles"})
public class FacturaVenta extends AuditoriaApp {

    // Es el numero de la factura, no su clave primaria.
    // La clave primaria es el id que heredamos de EntityId.
    private Long numero;

    // La fecha de emision es obligatoria.
    @Column(nullable = false)
    // Guarda la fecha con hora.
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    // Muchas facturas pueden corresponder al mismo punto de venta.
    // Si el punto de venta es nuevo, PERSIST permite guardarlo tambien.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID del punto de venta.
    @JoinColumn(name = "punto_venta_id", nullable = false)
    private PuntoVenta puntoVenta;

    // Estos campos simples tambien se guardan como columnas.
    private double importeCobrado;
    private double importeSaldo;

    // El importe total no puede almacenarse como null.
    @Column(nullable = false)
    private double importeTotal;

    private String cae;

    // Guarda la fecha de vencimiento del CAE con fecha y hora.
    // Puede quedar en null.
    @Temporal(TemporalType.TIMESTAMP)
    private Date caeFechaVencimiento;

    private String resultadoAfip;
    private String motivoRechazo;

    // El estado de la factura es obligatorio.
    @Column(nullable = false)
    private String estado;

    // Puede quedar en null mientras la factura no este anulada.
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnulacion;

    private String observaciones;

    // Una factura puede tener muchos detalles.
    // mappedBy indica que la relacion se controla desde el atributo
    // "factura" de la clase FacturaVentaDetalle.
    // ALL propaga a los detalles las operaciones de JPA:
    // persistir, fusionar, eliminar, refrescar y separar del contexto.
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    // Inicializamos la lista para poder agregar detalles sin que sea null.
    private List<FacturaVentaDetalle> detalles = new ArrayList<>();

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Recibe los datos iniciales de la factura.
    // Los importes llegan calculados: este constructor solo los asigna.
    public FacturaVenta(Long numero, Date fechaEmision,
                        PuntoVenta puntoVenta,
                        double importeCobrado, double importeSaldo,
                        double importeTotal, String estado,
                        Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.puntoVenta = puntoVenta;
        this.importeCobrado = importeCobrado;
        this.importeSaldo = importeSaldo;
        this.importeTotal = importeTotal;
        this.estado = estado;
    }

    // Agrega un detalle y mantiene ambos lados de la relacion.
    public void agregarDetalle(FacturaVentaDetalle detalle) {

        // Evita intentar agregar un detalle que no existe.
        if (detalle == null) {
            throw new IllegalArgumentException(
                "El detalle no puede ser null"
            );
        }

        // Evita usar un detalle que ya pertenece a otra factura.
        if (detalle.getFactura() != null
                && detalle.getFactura() != this) {
            throw new IllegalArgumentException(
                "El detalle ya pertenece a otra factura"
            );
        }

        // Agrega el detalle si ese objeto todavia no esta en la lista.
        if (!detalles.contains(detalle)) {
            detalles.add(detalle);
        }

        // Completa el otro lado: el detalle tambien conoce a su factura.
        // "this" representa la factura sobre la que llamamos al metodo.
        detalle.setFactura(this);
    }

    // Permite consultar los detalles sin modificar directamente la lista.
    // Para agregarlos usamos agregarDetalle y mantenemos ambos lados.
    public List<FacturaVentaDetalle> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }
}