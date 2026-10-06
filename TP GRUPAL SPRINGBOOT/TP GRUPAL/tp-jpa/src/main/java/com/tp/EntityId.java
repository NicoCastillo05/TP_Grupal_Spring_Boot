package com.tp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Permite que las clases hijas hereden los atributos y sus anotaciones.
// No se crea una tabla propia para EntityId.
@MappedSuperclass
// Es abstracta porque no necesitamos crear objetos EntityId directamente.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityId {

    // Marca este atributo como la clave primaria.
    @Id
    // La base de datos genera el ID al insertar un registro.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // protected permite acceder al atributo desde las clases hijas.
    protected Long id;
}