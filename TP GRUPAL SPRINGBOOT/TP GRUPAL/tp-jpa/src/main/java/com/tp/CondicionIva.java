package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre condiciones_iva a la tabla.
@Table(name = "condiciones_iva")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CondicionIva extends AuditoriaApp {

    // El codigo no puede almacenarse como null.
    @Column(nullable = false)
    private int codigoAfip;

    // La denominacion no puede almacenarse como null.
    @Column(nullable = false)
    private String denominacion;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear una condicion de IVA con sus datos.
    public CondicionIva(int codigoAfip, String denominacion,
                        Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.codigoAfip = codigoAfip;
        this.denominacion = denominacion;
    }
}