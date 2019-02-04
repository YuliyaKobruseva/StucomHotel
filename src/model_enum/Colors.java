/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_enum;

/**
 * Class with enum of colors available that allow retrieve code of color
 *
 * @author Yuli
 */
public enum Colors {

    RESET("\033[0m"),
    MAGENTA("\033[0;35m"),
    RED("\033[0;31m"),
    BLUE("\033[0;34m");

    private final String code;

    Colors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
