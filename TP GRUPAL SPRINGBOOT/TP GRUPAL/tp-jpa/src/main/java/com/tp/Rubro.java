package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre rubros a la tabla.
@Table(name = "rubros")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Rubro extends AuditoriaApp {

    // La denominacion es obligatoria.
    @Column(nullable = false)
    private String denominacion;

    // El codigo es obligatorio.
    @Column(nullable = false)
    private Integer codigo;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear un rubro con sus datos.
    public Rubro(String denominacion, Integer codigo,
                 Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.denominacion = denominacion;
        this.codigo = codigo;
    }
}