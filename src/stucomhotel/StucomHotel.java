/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stucomhotel;

import controller.Manager;
import exceptions.PersistenceException;
import exceptions.StucomHotelException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Yuli
 */
public class StucomHotel {

    private static Manager manager;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean exit = false;
            manager = new Manager();
            manager.setMoney(1000);

            try {
                manager.initData();
            } catch (StucomHotelException ex) {
                System.out.println(ex.getMessage());
            }
            while (!exit) {
                try {
                    String line = br.readLine();
                    String[] command = line.split(" ");
                    if (command.length > 0) {
                        exit = checkCommand(command);
                    } else {
                        throw new StucomHotelException(StucomHotelException.WRONG_COMMAND);
                    }
                } catch (StucomHotelException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    throw new PersistenceException("Fatal error: " + ex.getMessage());
                }
            }
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param command
     * @return
     * @throws StucomHotelException
     */
    private static boolean checkCommand(String[] command) throws StucomHotelException {
        String commandUser = command[0];
        switch (commandUser.toUpperCase()) {
            case "ROOM":
                commandCreation(command);
                break;
            case "WORKER":
                commandCreation(command);
                break;
            case "RESERVATION":

                break;
            case "HOTEL":

                break;
            case "PROBLEM":

                break;
            case "REQUEST":
                break;
            case "FINISH":
                break;
            case "LEAVE":
                break;
            case "MONEY":
                return money(command);
            case "EXIT":
                return exit(command);
            default:
                throw new StucomHotelException(StucomHotelException.WRONG_COMMAND);
        }
        return false;
    }

    private static void commandCreation(String[] command) throws StucomHotelException {
        
        if (command.length == 4) {
            String typeCommand = command[0];
            if(typeCommand.equalsIgnoreCase("ROOM")){
                
            }
            
        }else {
                throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
            }
    }

    private static boolean showMoney() {
        int money = manager.getMoney();
        if (money > 0) {
            System.out.println("=======================================================");
            System.out.println("==>   MONEY : " + money + " €   <==");
            System.out.println("=======================================================");
            return false;
        } else {
            System.out.println("=======================================================");
            System.out.println("==========    YOU´VE LOST ALL YOUR MONEY    ===========");
            System.out.println("=======================================================");
            return true;
        }
    }

    private static boolean money(String[] command) throws StucomHotelException {
        if (command.length == 1) {
            return showMoney();
        }
        throw new StucomHotelException(StucomHotelException.WRONG_NUMBER_ARGUMENTS);
    }

    /**
     *
     * @param command
     * @return
     * @throws StucomHotelException
     */
    private static boolean exit(String[] command) throws StucomHotelException {
        if (command.length == 1) {
            return true;
        }
        throw new StucomHotelException(StucomHotelException.WRONG_NUMBER_ARGUMENTS);
    }
}
