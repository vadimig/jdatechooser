/*
 * UncompatibleDataExeption.java
 *
 * Created on 8 ќкт€брь 2006 г., 7:47
 *
 */

package datechooser.model.exeptions;

/**
 * Throwed at attempt to set dates not compatible with current model state.<br>
 * »сключение, возникающее при попытке установить даты, проитворечащие текущему
 * состо€нию модели.
 * @author Androsov Vadim
 * @since 1.0
 */
public class IncompatibleDataExeption extends CalendarModelExeption {
    
    public IncompatibleDataExeption() {
    }
    
    public IncompatibleDataExeption(String mess) {
        super(mess);
    }
    
}
