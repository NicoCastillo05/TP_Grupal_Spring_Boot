package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre articulos a la tabla.
@Table(name = "articulos")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Articulo extends AuditoriaApp {

    // Muchos articulos pueden pertenecer al mismo rubro.
    // Si el rubro es nuevo, PERSIST permite guardarlo junto al articulo.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda el ID del rubro en la columna rubro_id.
    // Puede quedar en null porque la consigna no lo marca obligatorio.
    @JoinColumn(name = "rubro_id")
    private Rubro rubro;

    // El codigo es obligatorio.
    @Column(nullable = false)
    private String codigo;

    // La denominacion es obligatoria.
    @Column(nullable = false)
    private String denominacion;

    // Muchos articulos pueden compartir la misma marca.
    // Si la marca es nueva, PERSIST permite guardarla junto al articulo.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda el ID de la marca. Esta relacion puede quedar en null.
    @JoinColumn(name = "marca_id")
    private Marca marca;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Recibe los datos del articulo y los objetos rubro y marca.
    public Articulo(String codigo, String denominacion,
                    Rubro rubro, Marca marca,
                    Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.codigo = codigo;
        this.denominacion = denominacion;
        this.rubro = rubro;
        this.marca = marca;
    }
}