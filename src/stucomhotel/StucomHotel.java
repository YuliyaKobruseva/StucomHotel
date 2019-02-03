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
import java.util.ArrayList;
import java.util.HashSet;
import model_enum.Colors;
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
                System.out.println(Colors.RED + ex.getMessage() + Colors.RESET);
            } catch (InputException ex) {
                System.out.println(Colors.RED + ex.getMessage() + Colors.RESET);
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
                    System.out.println(Colors.RED + ex.getMessage() + Colors.RESET);
                } catch (IOException ex) {
                    throw new PersistenceException(Colors.RED + "Fatal error: " + ex.getMessage() + Colors.RESET);
                } catch (ManagerException ex) {
                    System.out.println(Colors.RED + ex.getMessage() + Colors.RESET);
                } catch (StucomHotelException ex) {
                    System.out.println(Colors.RED + ex.getMessage() + Colors.RESET);
                }
            }
        } catch (PersistenceException ex) {
            System.out.println(Colors.RED + ex.getMessage() + Colors.RESET);
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
                newReservation(command);
                break;
            case "HOTEL":
                hotel(command);
                break;
            case "PROBLEM":
                problem(command);
                break;
            case "REQUEST":
                request(command);
                break;
            case "FINISH":
                finishService(command);
                break;
            case "LEAVE":
                return leaveHotel(command);
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
        String numberRoom;
        if (command.length == 4) {
            if (Tools.convertStringToNumber(command[1]) < 10 && Tools.convertStringToNumber(command[1]) > 0) {
                numberRoom = "00" + command[1];
            } else if (Tools.convertStringToNumber(command[1]) > 10 && Tools.convertStringToNumber(command[1]) < 100) {
                numberRoom = "0" + command[1];
            } else {
                throw new InputException(InputException.WRONG_DATA);
            }
            String[] servicesCommand = command[3].split(",");
            for (String service : servicesCommand) {
                Services serviceCommand = manager.checkServiceRoomExist(service);
                if (serviceCommand != null) {
                    servicesRoom.add(serviceCommand);
                }
            }
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }
        manager.createRoom(command[1], Tools.convertStringToNumber(command[2]), servicesRoom);
    }

    private static void creationWorker(String[] command) throws InputException, ManagerException, StucomHotelException {
        HashSet<Skills> skillsWorker = new HashSet();
        if (command.length == 4) {
            String[] skillsCommand = command[3].split(",");
            for (String skill : skillsCommand) {
                if (manager.checkSkillExist(skill) != null) {
                    Skills serviceCommand = manager.checkSkillExist(skill);
                    skillsWorker.add(serviceCommand);
                }
            }
            manager.createWorker(command[1], command[2], skillsWorker);
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }

    }

    private static void newReservation(String[] command) throws InputException, ManagerException, StucomHotelException {
        HashSet<Services> reservationRequests = new HashSet();
        if (command.length == 4) {
            String[] requests = command[3].split(",");
            for (String request : requests) {
                if (manager.checkServiceRoomExist(request) != null) {
                    Services reservationRequest = manager.checkServiceRoomExist(request);
                    reservationRequests.add(reservationRequest);
                }
            }
            manager.reservation(command[1], Tools.convertStringToNumber(command[2]), reservationRequests);
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }
    }

    private static void problem(String[] command) throws InputException, ManagerException {
        if (command.length == 2) {
            manager.problemInRoom(command[1]);
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }
    }

    private static void request(String[] command) throws InputException, ManagerException, StucomHotelException {
        ArrayList<Skills> requestsPending = new ArrayList<>();
        if (command.length == 3) {
            String[] newRequests = command[2].split(",");
            for (String request : newRequests) {
                Skills newRequest = manager.checkSkillExist(request);
                if (newRequest != null) {
                    requestsPending.add(newRequest);
                } else {
                    throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
                }
            }
            manager.additionalRequestRoom(command[1], requestsPending);
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }
    }

    private static void hotel(String[] command) throws InputException {
        if (command.length == 1) {
            manager.showDataOfRoomsAndWorkers();
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }
    }

    private static void finishService(String[] command) throws InputException, ManagerException {
        if (command.length == 2) {
            manager.finishServicesRoom(command[1]);
        } else {
            throw new InputException(InputException.WRONG_NUMBER_ARGUMENTS);
        }
    }

    private static boolean leaveHotel(String[] command) throws InputException, ManagerException {
        if (command.length == 3) {
            return manager.leaveRoom(command[1], Tools.convertStringToNumber(command[2]));
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
