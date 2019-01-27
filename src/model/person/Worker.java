/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.person;

import java.util.HashSet;
import model_enum.Skills;

/**
 *
 * @author Yuli
 */
public class Worker extends Person {

    private String name;
    private HashSet<Skills> skills;

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

    public Worker(String DNI, String name, HashSet<Skills> skills) {
        super(DNI);
        this.name = name;
        this.skills = skills;
    }

}
