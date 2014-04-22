/*
 * Pictures.java
 *
 * Created on 9 Ноябрь 2006 г., 8:52
 *
 */

package datechooser.beans.pic;

import java.net.URL;

/**
 * Simplifies resources use.<br>
 * Класс, предназначеный для удобного обращения к ресурсам.
 * Т.е. при перемещении этого пакета, исходный код по всей программе 
 * будет корректен при условии поддержки средой рефакторинга перемещения.
 * @author Androsov Vadim
 * @since 1.0
 */
public class Pictures {
    
    public static URL getResource(String name) {
        return Pictures.class.getResource(name);
    }
    
    public static URL getDefaultPicture() {
        return getResource("java_logo.png");
    }
}
