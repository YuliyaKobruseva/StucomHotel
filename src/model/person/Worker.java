/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.person;

import java.util.HashSet;
import model.room.Room;
import model_enum.Skills;

/**
 * Class to define worker object
 *
 * @author Yuli
 */
public class Worker extends Person {

    private String name;
    private HashSet<Skills> skills;
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
     * Set the value of numberRoom
     *
     */
    public void setNumberRoom() {
        this.numberRoom = null;
    }

    /**
     * Get the value of skills
     *
     * @return the value of skills
     */
    public HashSet<Skills> getSkills() {
        return skills;
    }

    /**
     * Set the value of skills
     *
     * @param skills new value of skills
     */
    public void setSkills(HashSet<Skills> skills) {
        this.skills = skills;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Constructor of class
     *
     * @param DNI person identifier
     * @param name name of person
     * @param skills skills of persoon
     */
    public Worker(String DNI, String name, HashSet<Skills> skills) {
        super(DNI);
        this.name = name;
        this.skills = skills;
        this.numberRoom = null;
    }

}
