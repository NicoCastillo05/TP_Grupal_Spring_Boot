package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre listas_precio a la tabla.
@Table(name = "listas_precio")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListaPrecio extends AuditoriaApp {

    // El codigo de la lista es obligatorio.
    @Column(nullable = false)
    private String codigo;

    // El nombre o denominacion de la lista es obligatorio.
    @Column(nullable = false)
    private String denominacion;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear una lista, por ejemplo una lista "Minorista".
    public ListaPrecio(String codigo, String denominacion,
                       Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.codigo = codigo;
        this.denominacion = denominacion;
    }
}