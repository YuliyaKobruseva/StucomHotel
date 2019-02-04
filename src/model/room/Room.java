/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.room;

import java.util.HashSet;
import model_enum.Services;
import model_enum.Conditions;

/**
 * Class to define object room
 *
 * @author Yuli
 */
public class Room implements Comparable<Room> {

    private final String number;
    private final int capacity;
    private final HashSet<Services> services;
    private Conditions condition;

    /**
     * Get the value of condition
     *
     * @return the value of condition
     */
    public Conditions getCondition() {
        return condition;
    }

    /**
     * Set the value of condition
     *
     * @param condition new value of condition
     */
    public void setCondition(Conditions condition) {
        this.condition = condition;
    }

    /**
     * Get the value of services
     *
     * @return the value of services
     */
    public HashSet<Services> getServices() {
        return services;
    }

    /**
     * Get the value of capacity
     *
     * @return the value of capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Get the value of number
     *
     * @return the value of number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Constructor of class
     *
     * @param number number of room
     * @param capacity capacity of room
     * @param services sercives available in room
     */
    public Room(String number, int capacity, HashSet<Services> services) {
        this.number = number;
        this.capacity = capacity;
        this.services = services;
        this.condition = Conditions.CLEAN;
    }

    /**
     * Compares this room with the specified room for order
     *
     * @param room the object to be compared
     * @return Returns a negative integer, zero, or a positive integer as this
     * room is less than, equal to, or greater than the specified room
     */
    @Override
    public int compareTo(Room room) {
        return Integer.compare(this.capacity, room.capacity);
    }

}
