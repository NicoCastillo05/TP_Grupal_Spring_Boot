package com.tp;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Las clases hijas heredan estos campos y sus anotaciones.
// No se crea una tabla propia para AuditoriaApp.
@MappedSuperclass
// Hereda de EntityId el ID y su configuracion como clave primaria.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public abstract class AuditoriaApp extends EntityId {

    // La fecha de alta es obligatoria.
    @Column(nullable = false)
    // Guarda el Date con fecha y hora.
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaAlta;

    // Puede quedar en null si el registro no fue dado de baja.
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaBaja;

    // La fecha de la ultima modificacion es obligatoria.
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaModificacion;

    // Muchos registros pueden haber sido creados por el mismo usuario.
    // PERSIST permite guardar tambien al usuario si es un objeto nuevo.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda el ID del usuario en esta columna.
    // No puede quedar en null porque debemos indicar quien lo creo.
    @JoinColumn(name = "usuario_carga_id", nullable = false)
    protected Usuario usuarioCarga;

    // Muchos registros pueden haber sido dados de baja por el mismo usuario.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Puede quedar en null si el registro no fue dado de baja.
    @JoinColumn(name = "usuario_baja_id")
    protected Usuario usuarioBaja;

    // Muchos registros pueden haber sido modificados por el mismo usuario.
    @ManyToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID de quien hizo la ultima modificacion.
    @JoinColumn(name = "usuario_modificacion_id", nullable = false)
    protected Usuario usuarioModificacion;

    // Inicializa los datos de auditoria cuando creamos un objeto.
    // (El constructor vacio protected lo genera Lombok via @NoArgsConstructor.)
    protected AuditoriaApp(Usuario usuarioAuditoria) {

        // Obtiene la fecha y hora actuales.
        Date ahora = new Date();

        // Al crear el registro, ambas fechas tienen el mismo valor.
        this.fechaAlta = ahora;
        this.fechaModificacion = ahora;

        // Al crearlo, el usuario de carga tambien es el de modificacion.
        this.usuarioCarga = usuarioAuditoria;
        this.usuarioModificacion = usuarioAuditoria;

        // Los datos de baja quedan en null.
        // Este constructor no actualiza la auditoria en cambios posteriores.
    }
}