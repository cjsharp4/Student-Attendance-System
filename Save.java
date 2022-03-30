/**
** Class creates a save file based on attendance and roster csv data
*
* @author Carson Sharp
* @ClassID 70605
* @Final Project 
*
 */

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

/**
* Adds all data from the roster and header structures into a csv file resembling the orignally used roster csv
*/
public class Save{

    /**
     * Function to save to a specific location
     */

    //saves in same path as roster
    // HELP HERE: There's a question mark right before the first entry - Is it a charset issue?
    public void saveFile(ArrayList<ArrayList<String>> roster, ArrayList<String> header) {
        String location = "";
        JFileChooser c = new JFileChooser();
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Demonstrate "Save" dialog:
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            location = c.getSelectedFile().toString();

            ArrayList<ArrayList<String>> finalSaveFile = new ArrayList<ArrayList<String>>((ArrayList) roster.clone());
            finalSaveFile.add(0, header);
            try {
                // Try changing the Charsets so it outputs everything properly?  I've tried most of the charsets to no avail
                FileWriter csvWriter = new FileWriter(location + "\\saveRoster.csv", StandardCharsets.US_ASCII);

                for (List<String> rowData : finalSaveFile) {
                    String row = "";
                    for (Object colData : rowData) {
                        row += String.valueOf(colData);
                        row += ",";
                    }
                    
                    csvWriter.append(row);
                    csvWriter.append("\n");
                }
                csvWriter.flush();
                csvWriter.close();
                JOptionPane.showMessageDialog(null, "File has been saved at: " + location + "/saveRoster.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

    }


}