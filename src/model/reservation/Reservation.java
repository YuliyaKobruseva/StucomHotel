/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.reservation;

import java.util.HashSet;
import model.person.Customer;
import model.room.Room;
import model_enum.Services;

/**
 *
 * @author Yuli
 */
public class Reservation {

    private final int numberReservation;
    private final Customer customer;
    private final Room room;
    private final HashSet<Services> requests;
    private final int numberPerson;
    private int additionalRequest;

    /**
     * Get the value of additionalRequest
     *
     * @return the value of additionalRequest
     */
    public int getAdditionalRequest() {
        return additionalRequest;
    }

    /**
     * Set the value of additionalRequest
     *
     * @param additionalRequest new value of additionalRequest
     */
    public void setAdditionalRequest(int additionalRequest) {
        this.additionalRequest = additionalRequest;
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

    public Reservation(int numberReservation, Customer customer, Room room, HashSet<Services> requests, int numberPerson) {
        this.numberReservation = numberReservation;
        this.customer = customer;
        this.room = room;
        this.requests = requests;
        this.numberPerson = numberPerson;
        this.additionalRequest = 0;
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
     * Get the value of customer
     *
     * @return the value of customer
     */
    public Customer getCustomer() {
        return customer;
    }

}
