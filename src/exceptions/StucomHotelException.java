/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Yuli
 */
public class StucomHotelException extends CodeException{
    
     // StucomHotelException codes
    public static final int WRONG_SERVICE = 0;
    
    
    

    // StucomHotelException messages
    private final List<String> messages = Arrays.asList(
            "<[ Wrong service ]>",
            "<[ Wrong number of arguments ]>");

    public StucomHotelException(int code) {
        super(code);
    }
    
     @Override
    public String getMessage() {
        return messages.get(getCode());
    }
}
