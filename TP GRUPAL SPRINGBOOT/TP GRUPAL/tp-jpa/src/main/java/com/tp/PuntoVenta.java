package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre puntos_venta a la tabla.
@Table(name = "puntos_venta")
// Hereda los campos de auditoria y, a traves de ellos, el ID.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PuntoVenta extends AuditoriaApp {

    // El numero no puede almacenarse como null.
    // Esta anotacion no obliga a que sea positivo ni unico.
    @Column(nullable = false)
    private int numero;

    // JPA guarda estos campos como columnas aunque no tengan @Column.
    // Como no son obligatorios, pueden quedar en null.
    private String descripcion;
    private String tipoEmision;
    private String domicilioComercial;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear un punto de venta con sus datos.
    public PuntoVenta(int numero, String descripcion,
                      String tipoEmision, String domicilioComercial,
                      Usuario usuarioAuditoria) {

        // Llama al constructor de AuditoriaApp para completar la auditoria.
        super(usuarioAuditoria);

        this.numero = numero;
        this.descripcion = descripcion;
        this.tipoEmision = tipoEmision;
        this.domicilioComercial = domicilioComercial;
    }
}