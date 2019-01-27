package exceptions;

/**
 * Fatal error with files
 * 
 * @author mfontana
 */
public class PersistenceException extends Exception {

    public PersistenceException(String message) {
        super(message);
    }
    
}
