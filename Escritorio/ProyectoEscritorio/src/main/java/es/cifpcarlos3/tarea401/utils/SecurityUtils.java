package es.cifpcarlos3.tarea401.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Esta clase tiene las herramientas para el tema de seguridad.
 * Básicamente la usamos para cifrar las contraseñas antes de mandarlas al
 * servidor,
 * para que nadie las vea en texto plano.
 */
public class SecurityUtils {

    /**
     * Este método cifra la contraseña usando el algoritmo "SHA-256".
     * Lo que hacemos es juntar el "usuario + contraseña" para crear lo que se llama
     * un "Salt".
     * Esto está guay porque si dos personas tienen la misma contraseña, al tener
     * usuarios
     * diferentes, el resultado cifrado (el hash) será distinto. ¡Más seguridad!
     * 
     * @param username El nombre del usuario.
     * @param password La contraseña que ha escrito el usuario.
     * @return El churro de texto cifrado en hexadecimal.
     */
    public static String hashPassword(String username, String password) {
        try {
            // Juntamos el nombre de usuario y la contraseña.
            String input = username + password;
            // Pedimos a Java el "motor" para encriptar en SHA-256.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Convertimos la mezcla a bytes y la encriptamos.
            byte[] hashBytes = md.digest(input.getBytes());

            // Ahora pasamos esos bytes raros a un texto que podamos leer (Hexadecimal).
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0'); // Si el número es pequeño, le ponemos un cero delante.
                hexString.append(hex);
            }
            return hexString.toString(); // Devolvemos el hash final.
        } catch (NoSuchAlgorithmException e) {
            // Esto solo saltaría si la versión de Java es rarísima y no tiene SHA-256.
            throw new RuntimeException("Uy, ha habido un lío con el algoritmo de cifrado.", e);
        }
    }
}
