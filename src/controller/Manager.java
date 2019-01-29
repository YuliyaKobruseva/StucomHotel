package controller;

import exceptions.InputException;
import exceptions.ManagerException;
import exceptions.PersistenceException;
import model.person.Customer;
import model.person.Worker;
import model.room.Room;
import exceptions.StucomHotelException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import model.reservation.Reservation;
import model_enum.Colors;
import model_enum.Conditions;
import model_enum.Services;
import model_enum.Skills;
import persistence.InputFile;

/**
 * Controller class. It is manager of app.
 *
 * @author mfontana
 */
public class Manager {

    // Workers of app
    private TreeMap<String, Worker> workers;
    // Customers of app
    private TreeMap<String, Customer> customers;
    // Rooms of app
    private TreeMap<String, Room> rooms;
    // Reservations of app
    private TreeMap<Integer, Reservation> reservations;
    //Money of app
    private int money;
    private int countReservation = 0;

    /**
     * Get the value of money
     *
     * @return the value of money
     */
    public int getMoney() {
        return money;
    }

    /**
     * Set the value of money
     *
     * @param money new value of money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Inicializar datos
     */
    public Manager() {
        workers = new TreeMap<>();
        customers = new TreeMap<>();
        rooms = new TreeMap<>();
        reservations = new TreeMap<>();
        countReservation++;
    }

    /**
     * This method creates folder data if doesn't exist and load data from files
     *
     * @throws exceptions.StucomHotelException
     * @throws PersistenceException if there is some problem with files
     */
    public void initData() throws StucomHotelException, PersistenceException, InputException {
        InputFile.readFromFile(rooms, workers);
    }

    public void createRoom(String number, int capacity, HashSet<Services> services) throws ManagerException {
        if (rooms.get(number) == null) {
            rooms.put(number, new Room(number, capacity, services));
            System.out.println(Colors.BLUE + "--> new Room added " + number + " <--" + Colors.RESET);
        } else {
            throw new ManagerException(ManagerException.ROOM_EXIST);
        }
    }

    public void createWorker(String dni, String name, HashSet<Skills> skills) throws ManagerException {
        if (workers.get(dni) == null) {
            workers.put(dni, new Worker(dni, name, skills));
            System.out.println(Colors.BLUE + "--> new Worker added " + dni + " <--" + Colors.RESET);
        } else {
            throw new ManagerException(ManagerException.WORKER_EXIST);
        }
    }

    public Customer createCustomer(String dni) throws ManagerException {
        if (customers.get(dni) == null) {
            Customer newCustomer = new Customer(dni);
            customers.put(dni, newCustomer);
            return newCustomer;
        } else {
            throw new ManagerException(ManagerException.CUSTOMER_EXIST);
        }
    }

    public void createReservation(int countReservation, String dni, int numberPerson, HashSet<Services> services) throws ManagerException {
        Customer reservationCustomer;
        Room roomAvailable;
        if (customers.get(dni) != null) {
            reservationCustomer = customers.get(dni);
        } else {
            reservationCustomer = createCustomer(dni);
        }

        if (checkFreeRoom(services, numberPerson) != null) {
            roomAvailable = checkFreeRoom(services, numberPerson);
            reservations.put((Integer) countReservation, new Reservation(countReservation, reservationCustomer, roomAvailable, services, numberPerson));
        } else {
            throw new ManagerException(ManagerException.ROOM_NOT_AVAILABLE);
        }
        countReservation++;
    }

    public void finishServicesRoom(String numberRoom) throws ManagerException {
        if (rooms.get(numberRoom) != null) {
            Room roomForFinish = rooms.get(numberRoom);
            reservations.values().forEach((numReservation) -> {
                if (rooms.get(numberRoom).getNumber().equalsIgnoreCase(numReservation.getRoom().getNumber())) {
                    roomForFinish.setCondition(Conditions.RESERVED);
                } else {
                    roomForFinish.setCondition(Conditions.CLEAN);
                }
            }); //cambiar si pasa si nay trabajadores en habitacion
            for (Worker workerInRoom : workers.values()) {
                if (workerInRoom.getNumberRoom().getNumber().equalsIgnoreCase(numberRoom)) {
                    workerInRoom.setNumberRoom();
                    System.out.println("Services finished in room: " + numberRoom);
                } else {
                    System.out.println("There aren´t workers in room");
                }
            }
        } else {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        }
    }

    public void showDataOfRoomsAndWorkers() {
        if (rooms.isEmpty() && workers.isEmpty()) {
            System.out.println("[ Information is not available ]");
        } else {
            if (rooms.isEmpty()) {
                System.out.println("[ Room's information is not available ]");
            } else {
                System.out.println("==> ROOMS <==");
                for (Room room : rooms.values()) {
                    if (reservations.isEmpty()) {
                        System.out.println("== " + room.getClass().getSimpleName().toUpperCase() + " " + room.getNumber() + " " + room.getCondition() + " ==");
                    } else {
                        for (Reservation reservation : reservations.values()) {
                            if (room.getNumber().equalsIgnoreCase(reservation.getRoom().getNumber())) {
                                System.out.println("== " + room.getClass().getSimpleName().toUpperCase() + " " + room.getNumber() + " "
                                        + reservation.getCustomer().getClass().getSimpleName().toUpperCase() + ":" + reservation.getCustomer().getDNI()
                                        + "(" + reservation.getNumberPerson() + ") ==");
                            }
                        }
                    }
                }
            }
            if (workers.isEmpty()) {
                System.out.println("[ Worker's information is not available ]");
            } else {
                System.out.println("=======================================================");
                System.out.println("==> WORKERS <==");
                for (Worker worker : workers.values()) {
                    if (worker.getNumberRoom() == null) {
                        System.out.println("== " + worker.getClass().getSimpleName().toUpperCase() + " " + worker.getDNI() + " " + worker.getName()
                                + " AVAILABLE ==");
                    } else {
                        System.out.println("== " + worker.getClass().getSimpleName().toUpperCase() + " " + worker.getDNI() + " " + worker.getName()
                                + " ROOM:" + worker.getNumberRoom() + " ==");
                    }
                }
            }
        }

    }

    private Room checkFreeRoom(HashSet<Services> services, int numberPersons) {
        ArrayList<Room> roomsFrees = new ArrayList<>();
        Room roomFreeAvailable = null;
        boolean containService = false;
        for (Room room : rooms.values()) {
            for (Services service : services) {
                containService = room.getServices().contains(service);
            }

            if (containService && room.getCapacity() == numberPersons) {
                roomsFrees.add(room);
            }
        }
        if (roomsFrees.size() > 0) {
            roomFreeAvailable = roomsFrees.get(0);
        }
        return roomFreeAvailable;
    }

    public boolean checkServiceRoomExist(String newServiceRoom) throws StucomHotelException {
        for (Services service : Services.values()) {
            if (service.toString().equals(newServiceRoom.toUpperCase())) {
                return true;
            }
        }
        throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
    }

    public boolean checkSkillExist(String skillWorker) throws StucomHotelException {
        if (Skills.valueOf(skillWorker) != null) {
            return true;
        }
        throw new StucomHotelException(StucomHotelException.WRONG_SKILL);
    }

    public boolean showMoney() {
        int moneyApp = getMoney();
        if (moneyApp > 0) {
            System.out.println("=======================================================");
            System.out.println("==>   MONEY : " + moneyApp + " €   <==");
            System.out.println("=======================================================");
            return false;
        } else {
            System.out.println("=======================================================");
            System.out.println("==========    YOU´VE LOST ALL YOUR MONEY    ===========");
            System.out.println("=======================================================");
            return true;
        }
    }

}
