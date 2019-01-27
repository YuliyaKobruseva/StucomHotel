package persistence;

import exceptions.PersistenceException;
import exceptions.StucomHotelException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import model.person.Worker;
import model.room.Room;
//import tools.Tools;

/**
 * Class to load and save data in files
 *
 * @author mfontana
 */
public class InputFile {

    private static final String SEPARATOR = File.separator;
    private static final String FOLDER_DATA = "data";

    /**
     * Create folder data if doesn't exist
     */
    public static void createFolderData() {
        File folder = new File(FOLDER_DATA);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    /**
     * Load data from file
     *
     * @throws exceptions.StucomHotelException
     * @throws PersistenceException if there is some problem with files
     */
    public static void readFromFile() throws StucomHotelException, PersistenceException {
        File fileData = new File(FOLDER_DATA + SEPARATOR + "P3_load_data.txt");
        if (fileData.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(fileData));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split("-");
                    if (data.length == 4) {
                        switch (data[0].toUpperCase()) {
                            case "ROOM":
                                break;
                            case "WORKER":
                                break;
                        }

                    }
                }
            } catch (IOException ex) {
                throw new PersistenceException("Fatal error: " + ex.getMessage());
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException ex) {
                    throw new PersistenceException("Fatal error: " + ex.getMessage());
                }
            }
        }
    }

}
