/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.person;

import model.room.Room;

/**
 *
 * @author Yuli
 */
public abstract class Person {

    private final String DNI;
    private Room numberRoom;

    /**
     * Get the value of numberRoom
     *
     * @return the value of numberRoom
     */
    public Room getNumberRoom() {
        return numberRoom;
    }

    /**
     * Set the value of numberRoom
     *
     * @param numberRoom new value of numberRoom
     */
    public void setNumberRoom(Room numberRoom) {
        this.numberRoom = numberRoom;
    }

    /**
     * Get the value of DNI
     *
     * @return the value of DNI
     */
    public String getDNI() {
        return DNI;
    }

    public Person(String DNI) {
        this.DNI = DNI;
    }

}
