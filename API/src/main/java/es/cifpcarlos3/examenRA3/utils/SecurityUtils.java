package es.cifpcarlos3.examenRA3.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Esta clase es la encargada de la seguridad en el servidor (la API).
 * Sirve para cifrar las contraseñas antes de guardarlas en la base de datos
 * o para comprobar si la contraseña que nos manda el usuario es correcta.
 */
public class SecurityUtils {

    /**
     * Este método hace el "hashing" de la contraseña usando SHA-256.
     * Al igual que en el programa de escritorio, mezclamos el nombre de usuario
     * con la contraseña para darle una capa extra de seguridad (un salt).
     * 
     * @param username El nombre de usuario que nos llega.
     * @param password La contraseña que queremos cifrar.
     * @return El hash final en formato hexadecimal.
     */
    public static String hashPassword(String username, String password) {
        try {
            // Unimos el usuario y la contraseña para el cifrado.
            String input = username + password;
            // Pedimos a Java el motor SHA-256.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Generamos el hash en bytes.
            byte[] hashBytes = md.digest(input.getBytes());

            // Pasamos los bytes a texto hexadecimal para que sea legible.
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0'); // Rellenamos con un 0 si el hexadecimal es de un solo carácter.
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Si por algún milagro Java no tiene SHA-256, soltamos el error.
            throw new RuntimeException("Error raro al configurar el algoritmo de hashing", e);
        }
    }
}
