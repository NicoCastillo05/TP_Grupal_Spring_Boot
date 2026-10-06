package com.tp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;

public class Main {

    // Es el punto de entrada: Java comienza a ejecutar el programa aca.
    public static void main(String[] args) {

        // Lee la unidad FacturacionPU del persistence.xml.
        // Inicia Hibernate y prepara las tablas segun nuestras entidades.
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("FacturacionPU");

        try {
            // El EntityManager permite guardar, buscar, modificar
            // y eliminar entidades en la base de datos.
            EntityManager em = emf.createEntityManager();

            try {
                // Inicia la transaccion que agrupa las operaciones.
                em.getTransaction().begin();

                // Creamos el usuario que figurara en los datos de auditoria.
                // Por ahora es un objeto Java: todavia no llamamos a persist.
                Usuario usuario = new Usuario(
                        "pedro",
                        "clave-practica",
                        "Pedro",
                        "Perez"
                );

                // Creamos el punto de venta.
                // El constructor recibe al usuario para completar la auditoria.
                PuntoVenta puntoVenta = new PuntoVenta(
                        1,
                        "Sucursal principal",
                        "Electronica",
                        "San Martin 123",
                        usuario
                );

                // Creamos un rubro y una marca que compartiran los articulos.
                Rubro rubro = new Rubro(
                        "Libreria",
                        1,
                        usuario
                );

                Marca marca = new Marca(
                        "Marca de prueba",
                        1,
                        usuario
                );

                // Creamos dos articulos.
                Articulo cuaderno = new Articulo(
                        "ART-001",
                        "Cuaderno",
                        rubro,
                        marca,
                        usuario
                );

                Articulo carpeta = new Articulo(
                        "ART-002",
                        "Carpeta",
                        rubro,
                        marca,
                        usuario
                );

                // Creamos una lista de precios.
                ListaPrecio lista = new ListaPrecio(
                        "LP-001",
                        "Minorista",
                        usuario
                );

                // Asignamos un precio a cada articulo dentro de esa lista.
                ListaPrecioArticulo precioCuaderno =
                        new ListaPrecioArticulo(
                                lista, 1000.0, cuaderno, usuario
                        );

                ListaPrecioArticulo precioCarpeta =
                        new ListaPrecioArticulo(
                                lista, 2000.0, carpeta, usuario
                        );

                // Creamos la cabecera de la factura.
                // Total: 2 cuadernos de 1000 + 1 carpeta de 2000 = 4000.
                // No se cobro nada, por eso el saldo tambien es 4000.
                FacturaVenta facturaVenta = new FacturaVenta(
                        1L,
                        new Date(),
                        puntoVenta,
                        0.0,        // Importe cobrado
                        4000.0,     // Importe saldo
                        4000.0,     // Importe total
                        "PENDIENTE",
                        usuario
                );

                // Primer detalle: dos cuadernos.
                // Para esta prueba usamos bonificacion e IVA en cero.
                FacturaVentaDetalle detalleCuaderno =
                        new FacturaVentaDetalle(
                                precioCuaderno,
                                "Dos cuadernos",
                                2.0,                              // Cantidad
                                precioCuaderno.getPrecioVenta(),  // Precio unitario
                                0.0,                              // Bonificacion
                                2000.0,                           // Importe neto
                                0.0,                              // Importe IVA
                                2000.0                            // Subtotal
                        );

                // Segundo detalle: una carpeta.
                FacturaVentaDetalle detalleCarpeta =
                        new FacturaVentaDetalle(
                                precioCarpeta,
                                "Una carpeta",
                                1.0,
                                precioCarpeta.getPrecioVenta(),
                                0.0,
                                2000.0,
                                0.0,
                                2000.0
                        );

                // Asociamos ambos lados de la relacion:
                // la factura contiene los detalles,
                // y cada detalle referencia a esta factura.
                facturaVenta.agregarDetalle(detalleCuaderno);
                facturaVenta.agregarDetalle(detalleCarpeta);

                // Es el UNICO llamado a persist del programa.
                // Las cascadas permiten guardar los detalles y los
                // demas objetos nuevos relacionados con la factura.
                em.persist(facturaVenta);

                // Confirma los cambios de la transaccion en la BD.
                em.getTransaction().commit();

                // El ID ya fue generado por la base de datos.
                System.out.println(
                        "Factura guardada. ID: " + facturaVenta.getId()
                );

                System.out.println(
                        "Detalles: " + facturaVenta.getDetalles().size()
                );

                System.out.println(
                        "Total: " + facturaVenta.getImporteTotal()
                );

            } catch (RuntimeException e) {

                // Si falla y la transaccion sigue activa,
                // deshacemos sus cambios para evitar un guardado parcial.
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }

                // Mostramos el error original al finalizar el programa.
                throw e;

            } finally {
                // Se ejecuta tanto si funciona como si ocurre un error.
                em.close();
            }

        } finally {
            // Cierra los recursos de la fabrica de EntityManagers.
            emf.close();
        }
    }
}