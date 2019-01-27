/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Yuli
 */
public class CodeException extends Exception {
    
    private final int CODE;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public int getCode() {
        return CODE;
    }

    public CodeException(int code) {
        this.CODE = code;
    }

}
