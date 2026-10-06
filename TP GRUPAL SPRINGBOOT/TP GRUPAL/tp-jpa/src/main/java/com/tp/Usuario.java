package com.tp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente
@Entity
// Le pone el nombre usuarios a la tabla
@Table(name = "usuarios")
// Extiende de EntityID, en este caso heredando el @Id y como
// esta configurado (como PK)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Usuario extends EntityId {

    // Cada @Column(nullable=false) nos indica que no se puede almacenar
    // nulos en esas columnas de datos
    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String clave;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    // El constructor vacio lo genera Lombok (@NoArgsConstructor);
    // JPA lo necesita para cuando hacemos consultas a la BD.
    public Usuario(String usuario, String clave,
                   String nombre, String apellido) {
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
        this.apellido = apellido;
    }
}