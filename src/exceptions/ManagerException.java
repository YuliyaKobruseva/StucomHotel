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
public class ManagerException extends CodeException {
    // ManagerException codes

    public static final int ROOM_EXIST = 0;
    public static final int WORKER_EXIST = 1;
    public static final int CUSTOMER_EXIST = 2;
    public static final int ROOM_NOT_AVAILABLE = 3;
    public static final int ROOM_NOT_EXIST = 4;

    // ManagerException messages
    private final List<String> messages = Arrays.asList(
            "[ Room already exist ]",
            "[ Worker already exist ]",
            "[ Customer already exist ]",
            "[ There isn't any room available. Customer not asigned. You've lost 100€ ]",
            "[ There isn't room with this number ]");

    public ManagerException(int code) {
        super(code);
    }

    @Override
    public String getMessage() {
        return messages.get(getCode());
    }
}
