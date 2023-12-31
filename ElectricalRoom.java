import java.util.Scanner; // import Scanner class
import java.util.InputMismatchException; // import InputMismatchException class
import java.util.ArrayList; // import ArrayList class

// class ElectricalRoom, is a subclass of the abstract EscapeRoom class to inherit attributes and methods
public class ElectricalRoom extends EscapeRoom {

    /* 
    constructor method, no new parameters are required compared to the constructor in the parent abstract class, 
    and the only parameters present are there to use the constructor from the super class, 
    
    this constructor will create a Electrical Room object, the difficulty and short description of the
    room is what makes it different compared to the constructor in the abstract parent class
    */
    public ElectricalRoom(String theName, Player[] thePlayersPlaying){
        super(theName, thePlayersPlaying); // the object will include the same attributes as objects from the superclass (EscapeRoom)
        
        this.difficulty = 6; // this attribute (difficulty level) is automatically set to 6 when an object of this class is created
        this.shortDescription = "A bustling hub of activity, where cables crisscross like busy highways."; // this attribute (short description) is automatically set to this message when an object of this class is created
    } // end constructor ElectricalRoom()
    
    
    @Override
    /*
    this method displays a long description of the escape room, the description includes a short passage about how the room looks,
    the objective of the task in this room, and what you need to do to successfully complete the task
    */
    public void displayLongDescription(){
        // display the longer description of the escape room
        System.out.println("The Electrical Room is a dark hub filled with the hum and buzz of transformers\nand circuit breakers, working tirelessly to distribute electricity to all of\nthe lights, appliances, and machines that make our lives easier.");
        
        // display the goal of the escape room
        System.out.println("\nGOAL:\nInside the Electrical Room, you'll find rows of breaker panels, your job is to\nbring power back to the building by fixing the disconnected cables. Of course,\nwires cannot be connected more than once.");
        System.out.println("\nConnect the correct wires together based on each of their colours. Coins will\nbe awarded based on how accurately the wires are connected.\n");
    } // end displayLongDescription()
    
    
    @Override
    /*
    this method holds the main functionality of the game for this escape room, 
    it first prints out graphics for the task, then allows the user to play the game, 
    then it checks the answers of every player, displays a scoreboard and awards coins
    */
    public void playRoom(){
        // declare and initialize Scanner object s for keyboard input
        Scanner s = new Scanner(System.in);
        
        // invoke the printFile method in the parent class and pass in the graphics text file as an argument - displays graphics for electrical room
        printFile("GraphicsElectricalRoom.txt");
        System.out.println("\n"); // formatting prints a new line
        
        // declare and initialize a 2D String array that holds the correct wire connections, (what letter and number go together)
        // each row represents a wire, each column represents the side of the wire (left-letters, right-numbers)
        String[][] correctWireConnections = {
            {"A", "4"},
            {"B", "3"},
            {"C", "1"},
            {"D", "2"}
        };
        
        // declare and initialize an integer array to store the numbers of the wire connections the user connects in order
        int[] guessedWireConnections = new int[4];
        
        // declare an ArrayList that stores the wires remaining, after a wire has been connected it is removed and can no longer be connected to another wire
        ArrayList<Integer> wiresRemaining = new ArrayList<Integer>();
        /*
        for loop adds the numbers of the correct wire connections in order to the ArrayList using the correctWireConnections array
        */
        for(int i = 0; i < correctWireConnections.length; i++){
            wiresRemaining.add(Integer.parseInt(correctWireConnections[i][1]));
        }
        
        // declare and initialize a 2D string array to store the results for each player playing this room
        // each row represents a player and each column represents whether or not they connected the correct wires together (4 wires)
        String[][] results = new String[this.playersPlaying.length][4];
        
        // GAME
        /*
        for loop, prompts the user to enter the number of the wire they want to connect to each of the alphabet wires, (4 wires, so loops 4 times for each one)
        for every step the program ensures that the input was valid and within range
        */
        for(int i = 0; i < correctWireConnections.length; i++){
            /*
            while loop prompts the user to enter the number of the wire and will loop until the user enters a valid number, 
            that is the input is both a number and within range of the number of wires presented. 
            If invalid input is entered, the user will be prompted again
            */
            while(true){
                // prompt the user to enter the number of the wire they choose to connect to another wire 
                System.out.print("What wire (#) does wire " + correctWireConnections[i][0] + " connect to? : ");
                /*
                try-catch block ensures that the user enters valid input (number) which is also within range
                catch block is executed if user enters input other than a number
                second catch block is executed if the number they entered is an array index out of bounds (executed if the wire number they entered cannot be removed from the arraylist)
                */
                try{
                    guessedWireConnections[i] = s.nextInt(); // get user input and store it at a specific index of the guessedWireConnections array
                    s.nextLine(); // fix glitch
                    
                    // after the number of the wire has been guessed, remove the guesed wire number from the array
                    wiresRemaining.remove(wiresRemaining.indexOf(guessedWireConnections[i])); 
                    
                    break;
                }
                catch(InputMismatchException e){
                    System.out.println("Invalid input. Please enter a number.\n"); // print error message
                    s.nextLine(); // fix glitch
                }
                catch(ArrayIndexOutOfBoundsException e){
                    /*
                    if else block checks if the number they entered is out of range of valid wire numbers
                    or if the user already guessed that wire number, it then displays the correct message respectivly
                    */
                    if(guessedWireConnections[i] < 1 || guessedWireConnections[i] > 4){
                        System.out.println("Invalid entry. Please try again.\n"); // output error message
                    }
                    else{
                        System.out.println("Sorry, you already connected that wire elsewhere.\n");// output error message
                    }
                }
            }
            System.out.println(""); // output a new line for formatting purposes
        } // end of for loop playing main game
        
        
        // CHECKING
        /*
        for loop, checks each of the players answers for every wire and assigns a check mark or cross emoji respectivly
        
        first part of the loop checks the main player (user)
        second part of the loop is a nested loop and checks that specific step for each of the bot players
        */
        for(int i = 0; i < correctWireConnections.length; i++){
            /*
            if-else block checks if the user entered the correct answer for a specific wire, 
            compares the answer the user entered with the correct wire answer
            
            if the user answered correctly a check mark emoji will be assigned to them for that question in the results array,
            and coins will be awarded based on the difficulty of the room
            
            otherwise, if the user answered incorrectly a cross will be assigned to them for that specific question in the results array
            */
            if(guessedWireConnections[i] == Integer.parseInt(correctWireConnections[i][1])){
                results[0][i] = "✔️ "; // assigns a checkmark if the user answered correctly to the array
                // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded for one right wire connection = 30, total max = 120
                this.playersPlaying[0].addCoins(this.difficulty*5);
            }
            else{
                results[0][i] = "❌ "; // assigns a cross if the user answered incorrectly to the array
            }
            
            /*
            for loop, randomly generates answers for the bots and checks their answers, if they got it right or wrong
            */
            for(int j = 1; j < this.playersPlaying.length; j++){
                Integer randomWire = (int)(Math.random()*4 + 1); // declare and initialize an integer variable to store a random number that counts as a valid answer within range
                /*
                if-else block checks if the bot player got the correct answer for a specific question, 
                compares the answer that was randomly generated with the correct potion recipe
                
                if the bot answered correctly a check mark emoji will be assigned to them for that question in the results array,
                and coins will be awarded based on the difficulty of the room
                
                otherwise, if the bot answered incorrectly a cross will be assigned to them for that specific question in the results array
                */
                if(randomWire == Integer.parseInt(correctWireConnections[i][1])){
                    results[j][i] = "✔️ "; // assigns a checkmark if the bot answered correctly to the array
                    // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded for one right wire connection = 30, total max = 120
                    this.playersPlaying[j].addCoins(this.difficulty*5);
                }
                else{
                    results[j][i] = "❌ "; // assigns a cross if the bot answered incorrectly to the array
                }
            }
        } // end of for loop checking answers
        
        
        // SCOREBOARD
        // prompt the user to press enter to see the results after playing the room
        System.out.print("Press enter to see results:");
        s.nextLine(); // allows user to press enter
        System.out.println("\nElectrical Mastery Scorecard:\n"); // display formatting subheading message
        System.out.println("[   WIRES   ][   PLAYER   ]"); // display formatting subheading message
        System.out.println("[A |B |C |D ]"); // display formatting subheading message
        
        /*
        for loop outputs a scoreboard with all of the players and check mark or cross based on whether or not they got the wire connections correct
        for loop loops as many times as there are players
        when displayed, each row represents the scoreboard for a specific player
        */
        for(int i = 0; i < results.length; i++){
            System.out.print("|"); // output a line for formatting purposes between each step 
            /*
            for loop goes through each question, since there are 4 wires it loops 4 times
            */
            for(int j = 0; j < 4; j++){
                System.out.print(results[i][j] + "|"); // output the emoji (checkmark/cross) and a line for formatting purposes between each step 
            }
            System.out.print(" - " + this.playersPlaying[i].getName() + "\n"); // display the player name of that score
        }
        
        System.out.println("\n💰  Coins were awarded based on Electrical Mastery score.\n"); // output message to inform user that coins were awarded
    } // end playRoom()
    
} // end ElectricalRoom subclass