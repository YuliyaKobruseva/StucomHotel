package exceptions;

import java.util.Arrays;
import java.util.List;
import model_enum.Colors;

/**
 * Exception of problems with commands
 * 
 * @author mfontana
 */
public class InputException extends CodeException {

    // Exception codes
       public static final int WRONG_COMMAND = 0;
       public static final int WRONG_NUMBER_ARGUMENTS = 1;
       public static final int WRONG_DATA = 2;

    // Exception messages
    private final List<String> messages = Arrays.asList(
            "<[ Wrong command ]>",
            "<[ Wrong number of arguments ]>",
            "<[ Incorrect data ]>");

    public InputException(int code) {
        super(code);
    }
    
     @Override
    public String getMessage() {
        return messages.get(getCode());
    }

}
