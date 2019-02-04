/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.person;

/**
 * Class to define person object
 *
 * @author Yuli
 */
public abstract class Person {

    private final String DNI;

    /**
     * Get the value of DNI
     *
     * @return the value of DNI
     */
    public String getDNI() {
        return DNI;
    }

    /**
     * Constructor of class
     *
     * @param DNI person identifier
     */
    public Person(String DNI) {
        this.DNI = DNI;
    }

}
