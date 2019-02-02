package persistence;

import exceptions.InputException;
import exceptions.PersistenceException;
import exceptions.StucomHotelException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import model.person.Worker;
import model.room.Room;
import model_enum.Services;
import model_enum.Skills;
import tools.Tools;

/**
 * Class to load and save data in files
 *
 * @author mfontana
 */
public class InputFile {

    private static final String SEPARATOR = File.separator;
    private static final String FOLDER_DATA = "datos";

    /**
     * Create folder data if doesn't exist
     */
//    public static void createFolderData() {
//        File folder = new File(FOLDER_DATA);
//        if (!folder.exists()) {
//            folder.mkdir();
//        }
//    }
    /**
     * Load data from file
     *
     * @param rooms
     * @param workers
     * @throws exceptions.StucomHotelException
     * @throws PersistenceException if there is some problem with files
     * @throws exceptions.InputException
     */
    public static void readFromFile(TreeMap<String, Room> rooms, HashMap<String, Worker> workers) throws StucomHotelException, PersistenceException, InputException {
        File fileData = new File(System.getProperty("user.dir") + SEPARATOR + FOLDER_DATA + SEPARATOR + "P3_load_data.txt");
        if (fileData.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(fileData));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(" ");
                    if (data.length == 4) {
                        switch (data[0].toUpperCase()) {
                            case "ROOM":
                                HashSet<Services> servicesRoom = new HashSet();
                                String[] servicesInit = data[3].split(",");
                                for (String service : servicesInit) {
                                    Services serviceCommand = Tools.converStringToEnumService(service);
                                    servicesRoom.add(serviceCommand);
                                }
                                rooms.put(data[1], new Room(data[1], Tools.convertStringToNumber(data[2]), servicesRoom));                                
                                break;
                            case "WORKER":
                                HashSet<Skills> skillsWorker = new HashSet();
                                String[] skillsCommand = data[3].split(",");
                                for (String skill : skillsCommand) {
                                    Skills serviceCommand = Tools.converStringToEnumSkill(skill);
                                    skillsWorker.add(serviceCommand);
                                }
                                workers.put(data[1], new Worker(data[1], data[2], skillsWorker));                                
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
