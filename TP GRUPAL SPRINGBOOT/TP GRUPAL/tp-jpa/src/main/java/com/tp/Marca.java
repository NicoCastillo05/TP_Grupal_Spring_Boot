package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre marcas a la tabla.
@Table(name = "marcas")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Marca extends AuditoriaApp {

    // La denominacion no puede almacenarse como null.
    @Column(nullable = false)
    private String denominacion;

    // El codigo tampoco puede almacenarse como null.
    @Column(nullable = false)
    private Integer codigo;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear una marca con su denominacion y codigo.
    public Marca(String denominacion, Integer codigo,
                 Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.denominacion = denominacion;
        this.codigo = codigo;
    }
}