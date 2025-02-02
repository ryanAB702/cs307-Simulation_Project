package Controller;

import javafx.scene.paint.Color;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public class FileCreator {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String EQUALS_DELIMITER = "=";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DATA_EXTENSION = "data\\";
    private static final String RESOURCES_EXTENSION = "src\\Resources\\";
    private static final String ERROR_MSG = "Error saving your simulation";

    /**
     * Writes a CSV file name fileName based on a Grid whose state values it stores
     * @param fileName
     * @param g
     */
    public static void writeCsvFile(String fileName, Grid g) throws SimulationException {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(String.format("%s%s%s", DATA_EXTENSION, fileName, ".csv"));
            //Add a new line separator after the header
            fileWriter.append(g.getMyRows() + "");
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(g.getMyCols() + "");
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for(int i=0; i<g.getMyRows(); i++){
                for(int j = 0;j<g.getMyCols(); j++){
                    fileWriter.append(g.getCellState(j, i) + "");
                    if(!(j==g.getMyCols()-1)) fileWriter.append(COMMA_DELIMITER);
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            } catch (IOException e) {
                throw new SimulationException(ERROR_MSG);
            } catch (NullPointerException e){
                throw new SimulationException(ERROR_MSG);
        } finally {
            closeWriter(fileWriter);
        }
    }

    /**
     * Writes a properties file named filename that references a CSV file, the simulation represented and the colors
     * in the GUI display
     * @param fileName
     * @param CsvFile
     * @param Simulation
     * @param cellColors
     */

    public static void writePropertiesFile(String fileName, String CsvFile, String Simulation, Map<Integer, Color> cellColors) throws SimulationException{
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(String.format("%s%s%s", RESOURCES_EXTENSION, fileName, ".properties"));
            fileWriter.append(String.format("%s%s%s", "Simulation", EQUALS_DELIMITER,  Simulation));
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(String.format("%s%s", "Name", EQUALS_DELIMITER));
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(String.format("%s%s", "Description", EQUALS_DELIMITER));
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(String.format("%s%s%s%s", "File",  EQUALS_DELIMITER, CsvFile, ".csv"));
            fileWriter.append(NEW_LINE_SEPARATOR);
            for(Map.Entry<Integer, Color> entry: cellColors.entrySet()){
                fileWriter.append(String.format("%s%s%s%s%s#%s", "Color", entry.getKey(), EQUALS_DELIMITER, entry.getKey(), COMMA_DELIMITER, entry.getValue().toString().substring(2,8).toUpperCase()));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            throw new SimulationException(ERROR_MSG);
        } catch (NullPointerException e){
            throw new SimulationException(ERROR_MSG);
        } finally {
            closeWriter(fileWriter);
        }
    }

    private static void closeWriter(FileWriter fileWriter){
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new SimulationException(ERROR_MSG);
        } catch (NullPointerException e){
            throw new SimulationException(ERROR_MSG);
        }
    }
}