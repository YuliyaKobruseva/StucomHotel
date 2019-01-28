package controller;

import exceptions.ManagerException;
import exceptions.PersistenceException;
import java.util.HashMap;
import model.person.Customer;
import model.person.Worker;
import model.room.Room;
import exceptions.StucomHotelException;
import java.util.ArrayList;
import java.util.HashSet;
import model.reservation.Reservation;
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
    private HashMap<String, Worker> workers;
    // Customers of app
    private HashMap<String, Customer> customers;
    // Rooms of app
    private HashMap<String, Room> rooms;
    // Reservations of app
    private HashMap<Integer, Reservation> reservations;
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
        workers = new HashMap<>();
        customers = new HashMap<>();
        rooms = new HashMap<>();
        countReservation++;
    }

    /**
     * This method creates folder data if doesn't exist and load data from files
     *
     * @throws exceptions.StucomHotelException
     * @throws PersistenceException if there is some problem with files
     */
    public void initData() throws StucomHotelException, PersistenceException {
        InputFile.readFromFile();
    }

    public void createRoom(String number, int capacity, HashSet<Services> services) throws ManagerException {
        if (rooms.put(number, new Room(number, capacity, services)) == null) {
            System.out.println("--> new Room added " + number + " <--");
        } else {
            throw new ManagerException(ManagerException.ROOM_EXIST);
        }
    }

    public void createWorker(String dni, String name, HashSet<Skills> skills) throws ManagerException {
        if (workers.put(dni, new Worker(dni, name, skills)) == null) {
        } else {
            throw new ManagerException(ManagerException.WORKER_EXIST);
        }
    }

    public Customer createCustomer(String dni) throws ManagerException {
        Customer newCustomer = new Customer(dni);
        if (customers.put(dni, newCustomer) == null) {
        } else {
            throw new ManagerException(ManagerException.CUSTOMER_EXIST);
        }
        return newCustomer;
    }

    public void createReservation(int countReservation, String dni, int numberPerson, HashSet<Services> services) throws ManagerException {
        Customer reservationCustomer;
        Room roomAvailable;
        if (checkCustomer(dni) != null) {
            reservationCustomer = checkCustomer(dni);
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

    private Customer checkCustomer(String dni) {
        Customer checkCustomer = null;
        for (Customer customer : customers.values()) {
            if (customer.getDNI().equalsIgnoreCase(dni)) {
                checkCustomer = customer;
            }
        }
        return checkCustomer;
    }
//
//    public Room checkRoom(String numberRoom) {
//        Room roomExist = null;
//        for (Room room : rooms.values()) {
//            if (room.getNumber().equalsIgnoreCase(numberRoom)) {
//                roomExist = room;
//            }
//        }
//        return roomExist;
//    }
//
//    public Worker checkWorker(String nameWorker, String dniWorker) {
//        Worker workerExist = null;
//        for (Worker worker : workers.values()) {
//            if (worker.getDNI().equalsIgnoreCase(dniWorker) && worker.getName().equalsIgnoreCase(nameWorker)) {
//                workerExist = worker;
//            }
//        }
//        return workerExist;
//    }

    public boolean checkServiceRoomExist(String newServiceRoom) throws StucomHotelException {
        boolean serviceExistApp = false;
        if (Services.valueOf(newServiceRoom) != null) {
            serviceExistApp = true;
        } else {
            throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
        }

        return serviceExistApp;
    }

    public boolean checkSkillExist(String skillWorker) throws StucomHotelException {
        boolean skillWorkerExist = false;
        if (Skills.valueOf(skillWorker) != null) {
            skillWorkerExist = true;
        } else {
            throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
        }
        return skillWorkerExist;
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
