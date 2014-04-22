/*
 * ControllerExeption.java
 *
 * Created on 3 Июль 2006 г., 18:58
 *
 */

package datechooser.controller;

/**
 * Controller exceptions.<br>
 * Исключения, возникающие при обработке событий.
 * @author Androsov Vadim
 * @since 1.0
 */
public class ControllerException extends Exception {
    
    public ControllerException(String message) {
        super(message);
    }
    
}
