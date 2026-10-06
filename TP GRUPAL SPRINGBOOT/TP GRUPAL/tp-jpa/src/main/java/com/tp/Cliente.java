package com.tp;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marca la clase como una entidad persistente.
@Entity
// Le pone el nombre clientes a la tabla.
@Table(name = "clientes")
// Hereda el ID y los datos de auditoria.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cliente extends AuditoriaApp {

    // El CUIT o CUIL es obligatorio.
    @Column(nullable = false)
    private String cuitCuil;

    // La denominacion es obligatoria.
    @Column(nullable = false)
    private String denominacion;

    // Un cliente tiene un contacto que no comparte con otros clientes.
    // Si el contacto es nuevo, PERSIST permite guardarlo junto al cliente.
    @OneToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID del contacto.
    // unique impide que otro cliente use ese mismo contacto.
    @JoinColumn(name = "contacto_id", nullable = false, unique = true)
    private Contacto contacto;

    // Un cliente tiene un domicilio que no comparte con otros clientes.
    // Si el domicilio es nuevo, PERSIST permite guardarlo tambien.
    @OneToOne(cascade = CascadeType.PERSIST)
    // Guarda obligatoriamente el ID del domicilio.
    // unique impide repetir ese ID en otro cliente.
    @JoinColumn(name = "domicilio_id", nullable = false, unique = true)
    private Domicilio domicilio;

    // El constructor vacio para JPA lo genera Lombok (@NoArgsConstructor).
    // Recibe los datos del cliente y sus objetos contacto y domicilio.
    public Cliente(String cuitCuil, String denominacion,
                   Contacto contacto, Domicilio domicilio,
                   Usuario usuarioAuditoria) {

        // Inicializa los datos de auditoria heredados.
        super(usuarioAuditoria);

        this.cuitCuil = cuitCuil;
        this.denominacion = denominacion;
        this.contacto = contacto;
        this.domicilio = domicilio;
    }
}