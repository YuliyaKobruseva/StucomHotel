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
 *
 * @author Yuli
 */
public class Room {

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

    public Room(String number, int capacity, HashSet<Services> services) {
        this.number = number;
        this.capacity = capacity;
        this.services = services;
        this.condition = Conditions.CLEAN;
    }

}
