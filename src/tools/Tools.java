package tools;

import exceptions.InputException;
import model_enum.Services;
import model_enum.Skills;

/**
 * Class with aux functions to validate data
 *
 * @author mfontana
 */
public class Tools {

    /**
     * Parse String to int
     *
     * @param text
     * @return value int of text
     * @throws InputException if String isn't a integer number, data wrong
     */
    public static int convertStringToNumber(String text) throws InputException {
        int num;
        try {
            num = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            throw new InputException(InputException.WRONG_DATA);
        }
        return num;
    }

    public static Services converStringToEnumService(String text) throws InputException {
        Services enumText;
        try {
            enumText = Services.valueOf(text.toUpperCase());
        } catch (NumberFormatException ex) {
            throw new InputException(InputException.WRONG_DATA);
        }
        return enumText;
    }

    public static Skills converStringToEnumSkill(String text) throws InputException {
        Skills enumText;
        try {
            enumText = Skills.valueOf(text.toUpperCase());
        } catch (NumberFormatException ex) {
            throw new InputException(InputException.WRONG_DATA);
        }
        return enumText;
    }
}
