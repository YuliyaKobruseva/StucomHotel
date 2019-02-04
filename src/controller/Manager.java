package controller;

import exceptions.InputException;
import exceptions.ManagerException;
import exceptions.PersistenceException;
import model.person.Customer;
import model.person.Worker;
import model.room.Room;
import exceptions.StucomHotelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import model.reservation.Reservation;
import model_enum.Colors;
import model_enum.Conditions;
import model_enum.Services;
import model_enum.Skills;
import persistence.InputFile;
import tools.Tools;

/**
 * Controller class. It is manager of app.
 *
 * @author Yuli
 */
public class Manager {

    // Workers of app
    private HashMap<String, Worker> workers;
    // Customers of app
    private HashMap<String, Customer> customers;
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
     * Initialize data
     */
    public Manager() {
        workers = new HashMap<>();
        customers = new HashMap<>();
        rooms = new TreeMap<>();
        reservations = new TreeMap<>();
        countReservation++;
    }

    /**
     * This method load data from file
     *
     * @throws exceptions.StucomHotelException if any data is wrong
     * @throws PersistenceException if there is some problem with files
     * @throws exceptions.InputException if any data is wrong
     */
    public void initData() throws StucomHotelException, PersistenceException, InputException {
        InputFile.readFromFile(rooms, workers);
    }

    /**
     * This method create new room in hotel
     *
     * @param number number of room
     * @param capacity capacity of room
     * @param services services available in room
     * @throws ManagerException warning message of app if there are duplicate
     * information
     */
    public void createRoom(String number, int capacity, HashSet<Services> services) throws ManagerException {
        if (rooms.get(number) == null) {
            rooms.put(number, new Room(number, capacity, services));
            System.out.println(Colors.BLUE + "--> new Room added " + number + " <--" + Colors.RESET);
        } else {
            throw new ManagerException(ManagerException.ROOM_EXIST);
        }
    }

    /**
     * This method create a new worker in hotel
     *
     * @param dni person identifier
     * @param name name of worker
     * @param skills skills of worker
     * @throws ManagerException warning message of app if there are duplicate
     * information
     */
    public void createWorker(String dni, String name, HashSet<Skills> skills) throws ManagerException {
        if (workers.get(dni) == null) {
            workers.put(dni, new Worker(dni, name, skills));
            System.out.println(Colors.BLUE + "--> new Worker added " + dni + " <--" + Colors.RESET);
        } else {
            throw new ManagerException(ManagerException.WORKER_EXIST);
        }
    }

    /**
     * This method create a new customer in registry of hotel
     *
     * @param dni person identifier
     * @return customer object
     * @throws ManagerException warning message of app if there are duplicate
     * information
     */
    public Customer createCustomer(String dni) throws ManagerException {
        if (customers.get(dni) == null) {
            Customer newCustomer = new Customer(dni);
            customers.put(dni, newCustomer);
            return newCustomer;
        } else {
            throw new ManagerException(ManagerException.CUSTOMER_EXIST);
        }
    }

    /**
     * This method create a new reservation in app
     *
     * @param dni customer identifier
     * @param numberPerson number of person for reservation
     * @param services requests of services for reservation
     * @throws ManagerException warning message of app if there are not
     * available room in hotel
     */
    public void reservation(String dni, int numberPerson, HashSet<Services> services) throws ManagerException {
        Customer reservationCustomer;
        Room roomAvailable;
        if (customers.get(dni) != null) {
            reservationCustomer = customers.get(dni);
        } else {
            reservationCustomer = createCustomer(dni);
        }
        if (checkFreeRoom(services, numberPerson) != null) {
            roomAvailable = checkFreeRoom(services, numberPerson);
            reservations.put(countReservation, new Reservation(countReservation, reservationCustomer, roomAvailable, services, numberPerson));
            roomAvailable.setCondition(Conditions.RESERVED);
            System.out.println(Colors.BLUE + "--> Assigned " + dni + " to Room " + roomAvailable.getNumber() + " <--" + Colors.RESET);
        } else {
            setMoney(getMoney() - 100);
            throw new ManagerException(ManagerException.ROOM_NOT_AVAILABLE);
        }
        countReservation++;
    }

    /**
     * This method warn about new problem in a room
     *
     * @param numberRoom number of room with problem
     * @throws exceptions.ManagerException warning message of app if there are
     * not available room in hotel to assign another room for customer
     */
    public void problemInRoom(String numberRoom) throws ManagerException {
        Room roomWithProblem = checkNumberRoom(numberRoom);
        if (roomWithProblem != null) {
            roomWithProblem.setCondition(Conditions.BROKEN);
            System.out.println(Colors.BLUE + "--> Room set as " + Conditions.BROKEN + " <--" + Colors.RESET);
            for (Reservation reservation : reservations.values()) {
                if (reservation.getRoom() != null) {
                    if (reservation.getRoom().getNumber().equalsIgnoreCase(numberRoom)) {
                        Room roomFree = checkFreeRoom(reservation.getRequests(), reservation.getNumberPerson());
                        if (roomFree != null) {
                            reservation.setRoom(roomFree);
                            System.out.println(Colors.BLUE + "--> Asigned " + reservation.getCustomer().getDNI() + " to Room" + roomFree.getNumber() + " <--" + Colors.RESET);
                        } else {
                            setMoney(getMoney() - 100);
                            reservations.remove(reservation.getNumberReservation());
                            throw new ManagerException(ManagerException.ROOM_NOT_AVAILABLE);
                        }
                    }
                }
            }
        } else {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        }
    }

    /**
     * This method add new customer request for room
     *
     * @param numberRoom number of room for request
     * @param requests new requests
     * @throws ManagerException warning message of app if number of room is
     * wrong
     */
    public void additionalRequestRoom(String numberRoom, ArrayList<Skills> requests) throws ManagerException {
        if (rooms.isEmpty()) {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        } else {
            Room room = checkNumberRoom(numberRoom);
            if (room != null) {
                Reservation numberReservationWithRoom = roomExistInReservation(room);
                for (Skills newCustomerRequest : requests) {
                    Worker workerAvailable = workerWithSkill(newCustomerRequest);
                    if (workerAvailable != null) {
                        workerAvailable.setNumberRoom(room);
                        System.out.println(Colors.BLUE + "--> Worker " + workerAvailable.getName() + " assigned to Room " + room.getNumber() + " <--" + Colors.RESET);
                    } else {
                        System.out.println(Colors.BLUE + "--> No Worker available for this service. Added to customer pending request <--" + Colors.RESET);
                    }

                    if (numberReservationWithRoom != null) {
                        numberReservationWithRoom.setAdditionalRequest(newCustomerRequest);
                    }
                }
            } else {
                throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
            }
        }
    }

    /**
     * This method informs that requests for a room have been finalized
     *
     * @param numberRoom number of room
     * @throws ManagerException warning message of app if number of room is
     * wrong
     */
    public void finishServicesRoom(String numberRoom) throws ManagerException {
        int couterWorkerInRoom = 0;
        if (rooms.get(numberRoom) != null) {
            for (Worker workerInRoom : workers.values()) {
                if (workerInRoom.getNumberRoom() != null) {
                    if (workerInRoom.getNumberRoom().getNumber().equalsIgnoreCase(numberRoom)) {
                        Room roomForFinish = rooms.get(numberRoom);
                        reservations.values().forEach((numReservation) -> {
                            if (rooms.get(numberRoom).getNumber().equalsIgnoreCase(numReservation.getRoom().getNumber())) {
                                roomForFinish.setCondition(Conditions.RESERVED);
                                numReservation.clearAdditionalRequest();
                            } else {
                                roomForFinish.setCondition(Conditions.CLEAN);
                            }
                        });
                        workerInRoom.setNumberRoom();
                        couterWorkerInRoom++;
                    }
                }
            }
        } else {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        }
        if (couterWorkerInRoom > 0) {
            System.out.println(Colors.BLUE + "--> Services finished in room: " + numberRoom + " <--" + Colors.RESET);
        } else {
            System.out.println(Colors.BLUE + "--> There aren't workers in room <--" + Colors.RESET);
        }
    }

    /**
     * This method informs that a customer is leaving the hotel and check money
     * of hotel
     *
     * @param numberRoom customer's number of room
     * @param money money to pay
     * @return true if hotel lost all money
     * @throws ManagerException warning message of app if number of room is
     * wrong
     */
    public boolean leaveRoom(String numberRoom, int money) throws ManagerException {
        Room roomCustomer = null;
        //quantity of additional requests
        int numberCustomerRequest = 0;
        int numberReservation = 0;
        ArrayList<Worker> workersInRoom = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getRoom().getNumber().equalsIgnoreCase(numberRoom)) {
                roomCustomer = reservation.getRoom();
                numberCustomerRequest = reservation.getAdditionalRequest().size();
                numberReservation = reservation.getNumberReservation();
            }
        }
        if (roomCustomer != null) {
            for (Worker workerInRoom : workers.values()) {
                if (workerInRoom.getNumberRoom() != null) {
                    if (workerInRoom.getNumberRoom().getNumber().equalsIgnoreCase(numberRoom)) {
                        workersInRoom.add(workerInRoom);
                        workerInRoom.setNumberRoom();
                    }
                }
            }
            rooms.get(numberRoom).setCondition(Conditions.UNCLEAN);
            reservations.remove(numberReservation);
        } else {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        }
        System.out.println(Colors.BLUE + "-> Room " + numberRoom + " free and set to " + Conditions.UNCLEAN + " <--");
        if (workersInRoom.size() == numberCustomerRequest) {
            System.out.println("--> Satisfaed clients. You win " + money + "€ <--" + Colors.RESET);
            setMoney(getMoney() + money);
        } else if (workersInRoom.size() < numberCustomerRequest) {
            System.out.println("--> Unsatisfaed clients. You loose " + money / 2 + "€ <--" + Colors.RESET);
            setMoney(getMoney() - money / 2);
            return messageLooseAllMoney(getMoney());
        }
        return false;
    }

    /**
     * This method show information about rooms and workers of hotel
     */
    public void showDataOfRoomsAndWorkers() {
        if (rooms.isEmpty() && workers.isEmpty()) {
            System.out.println(Colors.BLUE + "[ Information is not available ]" + Colors.RESET);
        } else {
            if (rooms.isEmpty()) {
                System.out.println(Colors.BLUE + "[ Room's information is not available ]" + Colors.RESET);
            } else {
                System.out.println(Colors.MAGENTA + "==> ROOMS <==" + Colors.RESET);
                rooms.values().forEach((room) -> {
                    if (reservations.isEmpty()) {
                        System.out.println(Colors.MAGENTA + "== " + room.getClass().getSimpleName().toUpperCase() + " " + room.getNumber() + " "
                                + room.getCondition() + " ==" + Colors.RESET);
                    } else {
                        Reservation reservation = roomExistInReservation(room);
                        if (reservation != null) {
                            System.out.println(Colors.MAGENTA + "== " + room.getClass().getSimpleName().toUpperCase() + " " + room.getNumber() + " "
                                    + reservation.getCustomer().getClass().getSimpleName().toUpperCase() + ":" + reservation.getCustomer().getDNI()
                                    + "(" + reservation.getNumberPerson() + ") ==" + Colors.RESET);
                        } else {
                            System.out.println(Colors.MAGENTA + "== " + room.getClass().getSimpleName().toUpperCase() + " " + room.getNumber() + " "
                                    + room.getCondition() + " ==" + Colors.RESET);
                        }
                    }
                });
            }
            if (workers.isEmpty()) {
                System.out.println(Colors.BLUE + "[ Worker's information is not available ]" + Colors.RESET);
            } else {
                System.out.println(Colors.MAGENTA + "=======================================================" + Colors.RESET);
                System.out.println(Colors.MAGENTA + "==> WORKERS <==" + Colors.RESET);
                workers.values().forEach((worker) -> {
                    if (worker.getNumberRoom() == null) {
                        System.out.println(Colors.MAGENTA + "== " + worker.getClass().getSimpleName().toUpperCase() + " " + worker.getDNI() + " " + worker.getName()
                                + " AVAILABLE ==" + Colors.RESET);
                    } else {
                        System.out.println(Colors.MAGENTA + "== " + worker.getClass().getSimpleName().toUpperCase() + " " + worker.getDNI() + " " + worker.getName()
                                + " ROOM:" + worker.getNumberRoom().getNumber() + " ==" + Colors.RESET);
                    }
                });
                System.out.println(Colors.MAGENTA + "=======================================================" + Colors.RESET);
            }
        }
    }

    /**
     * This method check available room for new reservation or to assign another
     * room in case of problem in room
     *
     * @param services requests of reservation
     * @param numberPersons number of person in reservation
     * @return room object
     * @throws ManagerException warning message of app if number of room is
     * wrong
     */
    private Room checkFreeRoom(HashSet<Services> services, int numberPersons) throws ManagerException {
        ArrayList<Room> roomsFrees = new ArrayList<>();
        Room roomFreeAvailable = null;
        if (!rooms.isEmpty()) {
            rooms.values().forEach((room) -> {
                int containServiceCounter = 0;
                for (Services service : services) {
                    if (room.getServices().contains(service)) {
                        containServiceCounter++;
                    }
                }
                if (containServiceCounter == services.size() && room.getCapacity() >= numberPersons && room.getCondition().toString().equalsIgnoreCase("CLEAN")) {
                    roomsFrees.add(room);
                }
            });
            if (roomsFrees.size() > 0) {
                Collections.sort(roomsFrees);
                roomFreeAvailable = roomsFrees.get(0);
            }
        } else {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        }
        return roomFreeAvailable;
    }

    /**
     * This method check services in room
     *
     * @param newServiceRoom service to check
     * @return service enum
     * @throws InputException if any data is wrong
     * @throws exceptions.StucomHotelException if this service doesn't exist in
     * hotel
     */
    public Services checkServiceRoomExist(String newServiceRoom) throws InputException, StucomHotelException {
        Services serviceExist = null;
        boolean exist = false;
        for (Services service : Services.values()) {
            if (service.toString().equalsIgnoreCase(newServiceRoom)) {
                serviceExist = Tools.converStringToEnumService(newServiceRoom);
                exist = true;
            }
        }
        if (!exist) {
            throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
        }
        return serviceExist;
    }

    /**
     * This method check if exist skill in hotel
     *
     * @param skillWorker
     * @return skill enum
     * @throws StucomHotelException if this skill doesn't exist in hotel
     * @throws exceptions.InputException if any data is wrong
     */
    public Skills checkSkillExist(String skillWorker) throws StucomHotelException, InputException {
        Skills skillExist = null;
        boolean exist = false;
        for (Skills skill : Skills.values()) {
            if (skill.toString().equalsIgnoreCase(skillWorker)) {
                skillExist = Tools.converStringToEnumSkill(skillWorker);
                exist = true;
            }
        }
        if (!exist) {
            //en mi app debe de ser WRONG_SKILL
            throw new StucomHotelException(StucomHotelException.WRONG_SERVICE);
        }

        return skillExist;
    }

    /**
     * This method show money of hotel
     *
     * @return true if hotel loose all money
     */
    public boolean showMoney() {
        int moneyApp = getMoney();
        if (moneyApp > 0) {
            System.out.println(Colors.MAGENTA + "=======================================================" + Colors.RESET);
            System.out.println(Colors.MAGENTA + "==>   MONEY : " + moneyApp + " €   <==" + Colors.RESET);
            System.out.println(Colors.MAGENTA + "=======================================================" + Colors.RESET);
            return false;
        } else {
            return messageLooseAllMoney(moneyApp);
        }
    }

    /**
     * This method show message that hotel loose all money
     *
     * @param money money of hotel available
     * @return true if hotel loose all money
     */
    private boolean messageLooseAllMoney(int money) {
        if (money < 0) {
            System.out.println(Colors.MAGENTA + "=======================================================" + Colors.RESET);
            System.out.println(Colors.MAGENTA + "==========    YOU´VE LOST ALL YOUR MONEY    ===========" + Colors.RESET);
            System.out.println(Colors.MAGENTA + "=======================================================" + Colors.RESET);
            return true;
        }
        return false;
    }

    /**
     * This method check worker with concrete skill
     *
     * @param skill skill for check
     * @return worker object
     */
    private Worker workerWithSkill(Skills skill) {
        ArrayList<Worker> workersAvailable = new ArrayList<>();
        Worker workerWithSkillAvalable = null;
        for (Worker worker : workers.values()) {
            if (worker.getSkills().contains(skill) && worker.getNumberRoom() == null) {
                workersAvailable.add(worker);
            }
        }
        if (workersAvailable.size() > 0) {
            workerWithSkillAvalable = workersAvailable.get(0);
        }
        return workerWithSkillAvalable;
    }

    /**
     * This method check if there is some reservation with concrete room
     *
     * @param room number of room for check
     * @return reservation object
     */
    private Reservation roomExistInReservation(Room room) {
        Reservation reservationWithRoom = null;
        for (Reservation reservation : reservations.values()) {
            if (room.getNumber() != null) {
                if (room.getNumber().equalsIgnoreCase(reservation.getRoom().getNumber())) {
                    reservationWithRoom = reservation;
                }
            }
        }
        return reservationWithRoom;
    }

    /**
     * This method check number of room in hotel
     *
     * @param numberRoom number of room
     * @return object room
     * @throws ManagerException warning message of app if number of room is
     * wrong
     */
    private Room checkNumberRoom(String numberRoom) throws ManagerException {
        Room roomExist = null;
        if (rooms.isEmpty()) {
            throw new ManagerException(ManagerException.ROOM_NOT_EXIST);
        } else {
            for (Room room : rooms.values()) {
                if (room.getNumber().equalsIgnoreCase(numberRoom)) {
                    roomExist = room;
                }
            }
        }
        return roomExist;
    }
}
