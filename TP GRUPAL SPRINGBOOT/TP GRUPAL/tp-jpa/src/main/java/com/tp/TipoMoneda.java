package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre tipos_moneda a la tabla.
@Table(name = "tipos_moneda")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TipoMoneda extends AuditoriaApp {

    // Los tres atributos son obligatorios:
    // no se pueden guardar como null en la BD.
    @Column(nullable = false)
    private String codigoAfip;

    @Column(nullable = false)
    private String denominacion;

    @Column(nullable = false)
    private String simbolo;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear un tipo de moneda con sus datos.
    public TipoMoneda(String codigoAfip, String denominacion,
                      String simbolo, Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.codigoAfip = codigoAfip;
        this.denominacion = denominacion;
        this.simbolo = simbolo;
    }
}