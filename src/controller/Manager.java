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

    public void createReservation(int countReservation, String dni, int numberPerson, HashSet<Services> services, HashSet<Customer> customers, HashSet<Room> rooms) throws ManagerException {
        Customer reservationCustomer;
        Room roomAvailable;
        if (checkCustomer(dni, customers) != null) {
            reservationCustomer = checkCustomer(dni, customers);
        } else {
            reservationCustomer = createCustomer(dni);
        }

        if (checkFreeRoom(services, rooms, numberPerson) != null) {
            roomAvailable = checkFreeRoom(services, rooms, numberPerson);
            reservations.put((Integer) countReservation, new Reservation(countReservation, reservationCustomer, roomAvailable, services, numberPerson));
        } else {
            throw new ManagerException(ManagerException.ROOM_AVAILABLE);
        }

    }

    private Room checkFreeRoom(HashSet<Services> services, HashSet<Room> rooms, int numberPersons) {
        ArrayList<Room> roomsFrees = new ArrayList<>();
        Room roomFreeAvailable = null;
        boolean containService = false;
        for (Room room : rooms) {
            for (Services service : services) {
                if (room.getServices().contains(service)) {
                    containService = true;
                } else {
                    containService = false;
                }
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

    private Customer checkCustomer(String dni, HashSet<Customer> customers) {
        Customer checkCustomer = null;
        for (Customer customer : customers) {
            if (customer.getDNI().equalsIgnoreCase(dni)) {
                checkCustomer = customer;
            }
        }
        return checkCustomer;
    }
}
