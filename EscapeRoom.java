import java.util.Scanner; // import Scanner class
import java.io.File; // import File class
import java.io.FileNotFoundException; // import FileNotFoundException class

// abstract class EscapeRoom
public abstract class EscapeRoom {
    
    // declare instance fields (attributes) for objects of this class
    // four attributes for a Room object (name, short description, difficulty, players playing)
    /*
    attributes are protected as opposed to private in order to use the same attributes in the subclasses, but also 
    to preserve encapsulation. making them protected made it so that attributes can be accessed by subclasses but 
    cannot be accessed from classes that aren't directly related to this EscapeRoom class
    
    protected attributes are much more efficent in this case/in my program since I want them to be accessible only 
    by subclasses and classes in the same package. (makes it less messy compared to using private)
    */
    protected String name;
    protected String shortDescription;
    protected int difficulty;
    protected Player[] playersPlaying;
    
    // constructor method, has the room name as a String parameter, and the players playing as an array of Player objects parameter
    public EscapeRoom(String theName, Player[] thePlayersPlaying){
        this.name = theName; // set the value for the name to be equal to the respective argument passed in
        this.shortDescription = ""; // initialize the short description to equal an empty string by default, will be overwritten in subclass
        this.difficulty = 0; // initialize the difficulty to equal 0 by default, will be overwritten in subclass
        this.playersPlaying = thePlayersPlaying; // set the value for the players playing to be equal to the respective argument passed in
    } // end constructor EscapeRoom()
    
    
    // this method gets the name of the EscapeRoom object and returns it
    public String getName(){
        return this.name; // returns name of the EscapeRoom object
    } // end getName()
    
    // this method gets the short description of the EscapeRoom object and returns it
    public String getShortDescription(){
        return this.shortDescription; // returns the short description of the EscapeRoom object
    } // end getShortDescription()
    
    // this method gets the difficulty level of the EscapeRoom object and returns it
    public int getDifficulty(){
        return this.difficulty; // returns the difficulty of the EscapeRoom object
    } // end getDifficulty()
    
    // this method sets the players playing and takes in the new players playing (updated array of players after elimination) as a parameter
    public void setPlayersPlaying(Player[] newPlayersPlaying){
        this.playersPlaying = newPlayersPlaying; // updates the players playing to the updated version of the players playing after elimination
    } // end setPlayersPlaying()
    
    /*
    this method (static method - can be called on the class itself, rather than on an instance of the class) 
    Static methods are typically used for utility functions that don't need to operate on specific instances of the class.
    
    this method takes in a textfile as an argument and prints the text within it, mainly for printing the graphics for each room
    */
    protected static void printFile(String file){
        /*
        try/catch block checks to see if the file exists, if not the catch block would be executed
        */
        try{
            File graphicsFile = new File(file); // create an object from the File class to store the file passed in
            Scanner f1 = new Scanner(graphicsFile); // declare and initialize a scanner object to scan the text in the text file
            
            // the while loop executes so long as we have a line of text to read in.
            while(f1.hasNextLine()){
                String line = f1.nextLine(); // stores the line read by the scanner in a variable
                System.out.println(line); // prints the stored line
            }
            f1.close(); // closes the scanner object for the file
        }
        catch(FileNotFoundException e){
            System.out.println("File does not exist"); // output error message
        }
    } // end printFile()
    
    // abstract method (procedure) displays a fun and creative long description of the Escape Room, the goal, and what you have to do
    public abstract void displayLongDescription();
    // abstract method (procedure) plays the Escape Room - or the task/game within the room. holds the bulk of the code for each room
    public abstract void playRoom();
    
} // end EscapeRoom abstract class