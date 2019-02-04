/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.reservation;

import java.util.ArrayList;
import java.util.HashSet;
import model.person.Customer;
import model.room.Room;
import model_enum.Services;
import model_enum.Skills;

/**
 * Class to define reservation object
 *
 * @author Yuli
 */
public class Reservation {

    private final int numberReservation;
    private final Customer customer;
    private Room room;
    private final HashSet<Services> requests;
    private final int numberPerson;
    private ArrayList<Skills> additionalRequest;

    /**
     * Get the value of additionalRequest
     *
     * @return the value of additionalRequest
     */
    public ArrayList getAdditionalRequest() {
        return additionalRequest;
    }

    /**
     * Set the value of additionalRequest
     *
     * @param additionalRequest new value of additionalRequest
     */
    public void setAdditionalRequest(Skills additionalRequest) {
        this.additionalRequest.add(additionalRequest);
    }

    /**
     * Method removes all of the elements from this list. The list will be empty
     * after this call returns.
     */
    public void clearAdditionalRequest() {
        this.additionalRequest.clear();
    }

    /**
     * Get the value of numberPerson
     *
     * @return the value of numberPerson
     */
    public int getNumberPerson() {
        return numberPerson;
    }

    /**
     * Get the value of numberReservation
     *
     * @return the value of numberReservation
     */
    public int getNumberReservation() {
        return numberReservation;
    }

    /**
     * Constructor of class
     *
     * @param numberReservation number of reservation
     * @param customer number of customer
     * @param room room available
     * @param requests requests fo reservation
     * @param numberPerson number of person with customer for reservation
     */
    public Reservation(int numberReservation, Customer customer, Room room, HashSet<Services> requests, int numberPerson) {
        this.numberReservation = numberReservation;
        this.customer = customer;
        this.room = room;
        this.requests = requests;
        this.numberPerson = numberPerson;
        this.additionalRequest = new ArrayList<>();
    }

    /**
     * Get the value of requests
     *
     * @return the value of requests
     */
    public HashSet<Services> getRequests() {
        return requests;
    }

    /**
     * Set the value of requests
     *
     * @param request add new value of requests
     */
    public void setRequest(Services request) {
        this.requests.add(request);
    }

    /**
     * Get the value of room
     *
     * @return the value of room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Set the value of Room
     *
     * @param room add new value of Room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Get the value of customer
     *
     * @return the value of customer
     */
    public Customer getCustomer() {
        return customer;
    }

}
