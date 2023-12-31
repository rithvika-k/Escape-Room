import java.util.Scanner; // import Scanner class
import java.util.InputMismatchException; // import InputMismatchException class

// class FishingRoom, is a subclass of the abstract EscapeRoom class to inherit attributes and methods
public class FishingRoom extends EscapeRoom {

    /* constructor method, no new parameters are required compared to the constructor in the parent abstract class, 
    and the only parameters present are there to use the constructor from the super class, 
    
    this constructor will create a Fishing Room object, the difficulty and short description of the
    room is what makes it different compared to the constructor in the abstract parent class
    */
    public FishingRoom(String theName, Player[] thePlayersPlaying){
        super(theName, thePlayersPlaying); // the object will include the same attributes as objects from the superclass (EscapeRoom)
        
        this.difficulty = 8; // this attribute (difficulty level) is automatically set to 8 when an object of this class is created
        this.shortDescription = "A place to immerse yourself in the hustle of fishing for dinner."; // this attribute (short description) is automatically set to this message when an object of this class is created
    } // end constructor FishingRoom()
    
    
    @Override
    /*
    this method displays a long description of the escape room, the description includes a short passage about how the room looks,
    the objective of the task in this room, and what you need to do to successfully complete the task
    */
    public void displayLongDescription(){
        // display the longer description of the escape room
        System.out.println("The Ice Fishing Room is a space designed to mimic the frozen lake with chilly\nconditions. It's a room filled with all the gear you need to drill through the\nice and reel in a trophy fish, from ice augers to rods and reels.");
        
        // display the goal of the escape room
        System.out.println("\nGOAL:\nInside, you are required to catch the most amount of fish through a hole in the\nice on a frozen body of water. It can be done with a variety of gear, and it\nrequires patience, skill, and a good sense of timing.");
        System.out.println("\nEach of the four ice holes below have their fish population represented by a\nmathematical expression. Select the ice hole that has the most amount of fish.\n");
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
        
        // invoke the printFile method in the parent class and pass in the graphics text file as an argument - displays graphics for fishing room
        printFile("GraphicsFishingRoom.txt");
        
        // declare and initialize integer variable to store the correct answer to the task in this escape room
        int holeWithMostFish = 2;
        // declare an integer variable to store the users answer for the task
        int guessHoleWithMostFish;
        
        // declare and initialize a string array to store the results for each player playing this room
        // each row represents a player and the column represents whether or not they guessed the correct ice fishing hole
        String[] results = new String[this.playersPlaying.length];
        
        // GAME
        /*
        while loop prompts the user to choose an ice fishing hole and will loop until the user enters a valid number, 
        that is the input is both a number and within range of the number of ice holes presented. 
        If invalid input is entered, the user will be prompted again
        */
        while(true){
            // prompt the user to choose an ice hole 
            System.out.print("\nWhich of the above ice fishing holes has the most amount of fish?: ");
            
            /*
            try-catch block ensures that the user enters valid input (number) which is also within range
            catch block is executed if user enters input other than a number
            */
            try{
                guessHoleWithMostFish = s.nextInt(); // get user input and store it in the guessHoleWithMostFish variable
                s.nextLine(); // fix glitch
                
                // if block ensures user enters a number within range, otherwise they will be prompted again
                if(guessHoleWithMostFish < 1 || guessHoleWithMostFish > 4){
                    System.out.println("Invalid entry. Please try again."); // output error message
                    continue; // goes back to the top of the loop to prompt the user to choose again 
                }
                break; // break out of while loop once user enters a valid answer
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number."); // print error message
                s.nextLine(); // fix glitch
            }
        }
        
        
        // CHECKING
        /*
        if-else block checks if the user entered the correct answer 
        compares the answer the user entered with the correct ice fishing hole 
        
        if the user answered correctly a check mark emoji will be assigned to them for that question in the results array,
        and coins will be awarded based on the difficulty of the room
        
        otherwise, if the user answered incorrectly a cross will be assigned to them for that specific question in the results array
        */
        if(guessHoleWithMostFish == holeWithMostFish){
            results[0] = "✔️ "; // assigns a checkmark if the user answered correctly to the array
            // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded in this room = 160
            this.playersPlaying[0].addCoins(this.difficulty*5*4);
        }
        else{
            results[0] = "❌ "; // assigns a cross if the user answered incorrectly to the array
        }
        
        /*
        for loop, randomly generates answers for the bots and checks their answers, if they got it right or wrong
        */
        for(int j = 1; j < this.playersPlaying.length; j++){
            int randomGuessHole = (int)(Math.random()*4 + 1);// declare and initialize an integer variable to store a random number that counts as a valid answer within range
            /*
            if-else block checks if the bot player got the correct answer for the question, 
            compares the answer that was randomly generated with the correct answer
            
            if the bot answered correctly a check mark emoji will be assigned to them for that question in the results array,
            and coins will be awarded based on the difficulty of the room
            
            otherwise, if the bot answered incorrectly a cross will be assigned to them for that specific question in the results array
            */
            if(randomGuessHole == holeWithMostFish){
                results[j] = "✔️ "; // assigns a checkmark if the bot answered correctly to the array
                // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded in this room = 160
                this.playersPlaying[j].addCoins(this.difficulty*5*4);
            }
            else{
                results[j] = "❌ "; // assigns a cross if the bot answered incorrectly to the array
            }
        }
        
        
        // SCOREBOARD
        System.out.print("\nPress enter to see results:"); // prompt the user to press enter to see the results after playing the room
        s.nextLine(); // allows user to press enter
        System.out.println("\nIce Fishing Mastery Scorecard:\n"); // display formatting subheading message
        System.out.println("[? ][   PLAYER   ]"); // display formatting subheading message
        
        /*
        for loop outputs a scoreboard with all of the players and check mark or cross based on whether or not they got the ice fishing hole correct
        for loop loops as many times as there are players
        when displayed, each row represents the scoreboard for a specific player
        */
        for(int i = 0; i < results.length; i++){
            System.out.print("|" + results[i] + "| - " + this.playersPlaying[i].getName() + "\n"); // display score and the player name 
        }
        
        System.out.println("\n💰  Coins were awarded based on Ice Fishing Mastery score.\n");// output message to inform user that coins were awarded
    } // end playRoom()
    
} // end FishingRoom subclass