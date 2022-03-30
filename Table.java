/**
** Class allows for dynamic creation of table data based on an initial roster and additional attendance data
*
* @author Carson Sharp
* @ClassID 70605
* @Final Project 
*
*/

import java.util.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

/**
* Table objects allow for the combination of roster and header data structures on the GUI.
*/
public class Table { 

    //Table contains the GUI with roster and header data
    private JTable table;
    
    //Table component is added to scroll before being passed back to Menu
    private JScrollPane scroll; 

    /**
    * Adds rows and new collumns together into one swing component
    */
    public Component getFullRoster(ArrayList<ArrayList<String>> roster, ArrayList<String> header){
       
        //Create JTable initial collumns
        table = new JTable(new DefaultTableModel(new Object[]{"id" , "First Name", "Last Name", "Program", "Level", "ASURITE"} , 0));
        table.setBounds(30,40,200,300);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        //Adds each collumn to the table
        for (int i = 6; i < header.size(); i++){
            model.addColumn(header.get(i));
        }

        //add rows from csv file 
        for (int i = 0; i < roster.size(); i++){
            model.addRow(roster.get(i).toArray());
        }

        //add JTable to JScrollPane and then repaint GUI
        scroll = new JScrollPane(table);
        return scroll;
    } 
}