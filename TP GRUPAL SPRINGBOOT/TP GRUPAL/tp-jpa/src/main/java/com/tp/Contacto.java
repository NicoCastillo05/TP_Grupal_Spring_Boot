package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre contactos a la tabla.
@Table(name = "contactos")
// Hereda el ID y su configuracion como clave primaria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Contacto extends EntityId {

    // JPA guarda estos atributos como columnas.
    // Pueden quedar en null porque no tienen nullable = false.
    private String email;
    private String telefono;
    private String celular;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear un contacto con sus datos.
    public Contacto(String email, String telefono, String celular) {
        this.email = email;
        this.telefono = telefono;
        this.celular = celular;
    }
}