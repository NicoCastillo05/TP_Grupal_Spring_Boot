package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre domicilios a la tabla.
@Table(name = "domicilios")
// Hereda el ID y su configuracion como clave primaria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Domicilio extends EntityId {

    // Se guarda como una columna de texto y puede quedar en null.
    private String nombreCalle;

    // Usamos String como indica la consigna.
    // Permite guardar numeros o textos como "S/N".
    private String numeroCalle;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Permite crear un domicilio con su calle y numero.
    public Domicilio(String nombreCalle, String numeroCalle) {
        this.nombreCalle = nombreCalle;
        this.numeroCalle = numeroCalle;
    }
}