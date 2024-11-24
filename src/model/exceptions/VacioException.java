package model.exceptions;

/**
 * Excepción lanzada cuando se intenta operar en una lista vacía.
 */
public class VacioException extends Exception {
    public VacioException(String message) {
        super(message);
    }
}
