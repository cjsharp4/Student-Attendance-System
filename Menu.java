/**
** Driver class that sets and controls all GUI elements in the program
*
* @author Carson Sharp
* @ClassID 70605
* @Final Project 
*
*/

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
*Initializes default GUI elements and creates action events when clicking the menu buttons
*/
public class Menu extends JFrame implements ActionListener {

    //GUI elements for the main frame, menu, and menu items
    private JFrame frame;
    private JMenuBar menubar;
    private JMenu file;
    private JRadioButtonMenuItem aboutMenu;
    private JMenuItem rosterMenu;
    private JMenuItem attendanceMenu;
    private JMenuItem saveMenu;
    private JMenuItem plotMenu;

    //two flags used to determine if a roster and attendance have been added yet
    boolean rosterLoaded = false; 
    boolean plotReady = false;

    //string containing the location to put the save file 
    private String saveLocation;

    //Two data structures used for the roster and header (attendance dates)
    private ArrayList<ArrayList<String>> rosterList = new ArrayList<>();
    private ArrayList<String> header = new ArrayList<>(Arrays.asList("ID", "First Name", "Last Name", "Program",
            "Level", "ASURITE"));

    //Creates Menu Object that contains initial GUI elements
    public Menu(){
        //Set up Frame/Window
        frame = new JFrame("CSE360 Final Project");
        //frame.setVisible(true);
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and add the menu bar to the frame
        menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        //Add menu item to the menu bar "File"
        file = new JMenu("File");
        menubar.add(file);

        //Add each sub-item that falls under "File"
        rosterMenu = new JMenuItem("Load a Roster");
        file.add(rosterMenu);
        attendanceMenu = new JMenuItem("Add Attendance");
        file.add(attendanceMenu);
        saveMenu = new JMenuItem("Save");
        file.add(saveMenu);
        plotMenu = new JMenuItem("Plot Data");
        file.add(plotMenu);

        //Add second menu item to main menu bar: "About"
        aboutMenu = new JRadioButtonMenuItem("About", null, false);

        // replace above line with: about = new JMenu("About") if we decide to add a submenu for about  
        menubar.add(aboutMenu);
        frame.setVisible(true);

        //Adds each menu to the actionlistener in the menuSelect class
        menuSelect menuSelect = new menuSelect();
        rosterMenu.addActionListener(menuSelect);
        attendanceMenu.addActionListener(menuSelect);
        saveMenu.addActionListener(menuSelect);
        plotMenu.addActionListener(menuSelect);
        aboutMenu.addActionListener(menuSelect);



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**Class containing our actionPerformed method. This will determine what to do when
    *a user clicks on anything in the GUI
    */
    private class menuSelect implements ActionListener{

        //component containing the roster with added headers
        Component aPanel;

        //component containing the plotted graph GUI
        Component aPlot;

        /**
        *handles all events caused by clicking a submenu item on the GUI
        */
        public void actionPerformed(ActionEvent event){

            //string that will contain each team member's name later
            String teamInfo = "";

            //Table object used to access functions that combine roster and attendance data
            Table table = new Table();

            //Determines which menu was selected and performs whatever is put in the
            //if statement for that event

            //Event if the roster menu is selected
            if(event.getSource() == rosterMenu){

                if(!rosterLoaded){

                    //Creates a roster object in order to obtain a roster GUI with csv data
                    Roster roster = new Roster();
                    rosterList = roster.loadRoster();
                    saveLocation = roster.getSavePath().toString();
                    aPanel = table.getFullRoster(rosterList, header);

                    //Adds GUI component to the frame and then repaints it
                    frame.add(aPanel);
                    frame.validate();
                    frame.repaint();

                    //flag determines that at least one roster was added so attendance can now be loaded
                    rosterLoaded = true;

                }else{

                    //Display message if a roster has already been loaded
                    JOptionPane.showMessageDialog(frame, "Roster already loaded!");
                }

            }
            
            //Event if the attendance menu is clicked
            else if(event.getSource() == attendanceMenu){

                //If at least one roster has been loaded, allow for attendance data to be added
                if(rosterLoaded){

                    //Creates new attendance object in order to load in attendance csv data
                    Attendance attendance = new Attendance();
                    rosterList = attendance.loadAttendance(rosterList, header);
                    header = attendance.getHeader();

                    //code to display users that connected but were not on the roster
                    String attendanceMessage = attendance.getAttendanceMessage().toString();
                    JOptionPane.showMessageDialog(frame, attendanceMessage);

                    //removes previous roster GUI and updates it to the new roster with added attendance
                    frame.remove(aPanel);
                    frame.revalidate();
                    frame.repaint();
                    aPanel = table.getFullRoster(rosterList, header);
                    frame.add(aPanel);
                    frame.validate();
                    frame.repaint(); 

                    //Once one attendance is loaded, plot can now be selected in the menu
                    plotReady = true;               

                }else{

                    //display message if no rosters have been added yet
                    JOptionPane.showMessageDialog(frame, "Wait! Try adding a roster first!");
                }
                
            }
            
            //Event if the save submenu is selected
            else if(event.getSource() == saveMenu){
                
                //if a roster has been loaded in, allow save function
                if(rosterLoaded){

                    Save save = new Save();
                    save.saveFile(rosterList, header);
                }else{

                    //Display this message if no roster data has been loaded in yet
                    JOptionPane.showMessageDialog(frame, "Nothing to save yet. Try loading a roster.");
                }
            }

            //Event if plot submenu is selected
            else if(event.getSource() == plotMenu){
                
                //if some attendance has been added, allow for a graph to be made
                if(plotReady){

                    //create new plot object with roster and header data so that a plot graph GUI component can be made
                    Plot plot = new Plot();
                    aPlot = plot.plotGraph(rosterList, header);
                    JOptionPane.showMessageDialog(frame, aPlot);
                }else{

                    //Display message if no attendance has been added yet
                    JOptionPane.showMessageDialog(frame, "Wait! Load some attendance to plot.");
                }
                
            }
            //Event if the about menu is selected. Displays team mates names.
            else if(event.getSource() == aboutMenu){  

                //Team names
                teamInfo = "Team:\nCarson Sharp\nRohit Nandakumar";
                //Displays dialog box with team information
                JOptionPane.showMessageDialog(frame, teamInfo);
            }

        }
    }

    //Driver class for program.
    public static void main(String[] args) {

        //calls Menu object to run instance of program
        new Menu();
    }

}