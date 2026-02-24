package com.example.tarearetrofit.model

import com.google.firebase.Timestamp

/**
 * Representa a un usuario (empleado) en el sistema.
 * Contiene información básica y el puesto que desempeña.
 */
data class User(
    val uid: String = "",
    val nombre: String = "",
    val apellido_1: String = "",
    val apellido_2: String = "------",
    val puesto: String = "",
    val dni: String = "",
    val telefono: String = "",
    val email: String = ""
)

/**
 * Representa una reserva de habitación realizada por un cliente.
 */
data class Reserva(
    val id_reserva: String = "",
    val uid: String = "", // UID del empleado que gestionó la reserva
    val dni_cliente: String = "",
    val numero_habitacion: Int = 0,
    val fecha_inicio: String = "",
    val fecha_fin: String = ""
)

/**
 * Representa el registro de una nómina asociada a un empleado.
 */
data class Nomina(
    val id_nomina: String = "",
    val uid: String = "", // UID del empleado dueño de la nómina
    val pago: Double = 0.0,
    val fecha: Timestamp = Timestamp.now()
)

/**
 * Representa una habitación del hotel.
 */
data class Habitacion(
    val id_habitacion: Int = 0,
    val disponible: Boolean = false
)

/**
 * Representa a un cliente del hotel.
 */
data class Cliente(
    val id_cliente: String = "",
    val nombre: String = "",
    val apellido_1: String = "",
    val apellido_2: String = "",
    val dni: String = "",
    val telefono: String = "",
    val email: String = ""
)

/**
 * Representa un evento en el calendario de turnos o estados.
 * Se utiliza para visualizar estados por fecha.
 */
data class CalendarEvent(
    val id: String = "",
    val uid: String = "",
    val date: String = "", // Formato: YYYY-MM-DD
    val state: String = "WHITE" // Estado visual (color) del día
)
