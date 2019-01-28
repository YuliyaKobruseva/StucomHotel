/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stucomhotel;

import controller.Manager;
import tools.Tools;
import exceptions.InputException;
import exceptions.ManagerException;
import exceptions.PersistenceException;
import exceptions.StucomHotelException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import model_enum.Services;
import model_enum.Skills;

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
                        throw new InputException(InputException.WRONG_COMMAND);
                    }
                } catch (InputException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    throw new PersistenceException("Fatal error: " + ex.getMessage());
                } catch (ManagerException ex) {
                    System.out.println(ex.getMessage());
                } catch (StucomHotelException ex) {
                    System.out.println(ex.getMessage());
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
    private static boolean checkCommand(String[] command) throws InputException, ManagerException, StucomHotelException {
        String commandUser = command[0];
        switch (commandUser.toUpperCase()) {
            case "ROOM":
                creationRoom(command);
                break;
            case "WORKER":
                creationWorker(command);
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
                throw new InputException(InputException.WRONG_COMMAND);
        }
        return false;
    }

    private static void creationRoom(String[] command) throws InputException, ManagerException, StucomHotelException {
        HashSet<Services> servicesRoom = new HashSet();
        if (command.length == 4) {

            String[] servicesCommand = command[3].split(",");
            for (String service : servicesCommand) {
                if (manager.checkServiceRoomExist(service)) {
                    Services serviceCommand = Tools.converStringToEnumService(service);
                    servicesRoom.add(serviceCommand);
                }
            }
            manager.createRoom(command[1], Tools.convertStringToNumber(command[2]), servicesRoom);
            System.out.println("--> new Room added " + command[1] + " <--");

        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }

    }

    private static void creationWorker(String[] command) throws InputException, ManagerException, StucomHotelException {
        HashSet<Skills> skillsWorker = new HashSet();
        if (command.length == 4) {
            String[] skillsCommand = command[3].split(",");
            for (String skill : skillsCommand) {
                if (manager.checkServiceRoomExist(skill)) {
                    Skills serviceCommand = Tools.converStringToEnumSkill(skill);
                    skillsWorker.add(serviceCommand);
                }
            }
            manager.createWorker(command[1], command[2], skillsWorker);
            System.out.println("--> new Worker added " + command[1] + " <--");
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }

    }

    private static boolean money(String[] command) throws InputException {
        if (command.length == 1) {
            return manager.showMoney();
        }
        throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
    }

    /**
     *
     * @param command
     * @return
     * @throws StucomHotelException
     */
    private static boolean exit(String[] command) throws InputException {
        if (command.length == 1) {
            return true;
        }
        throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
    }
}
