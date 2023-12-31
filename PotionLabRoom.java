import java.util.Scanner; // import Scanner class
import java.util.InputMismatchException; // import InputMismatchException class

// class PotionLabRoom, is a subclass of the abstract EscapeRoom class to inherit attributes and methods
public class PotionLabRoom extends EscapeRoom {
    
    /* constructor method, no new parameters are required compared to the constructor in the parent abstract class, 
    and the only parameters present are there to use the constructor from the super class, 
    
    this constructor will create a Potion Lab Room object, the difficulty and short description of the
    room is what makes it different compared to the constructor in the abstract parent class
    */
    public PotionLabRoom(String theName, Player[] thePlayersPlaying){
        super(theName, thePlayersPlaying); // the object will include the same attributes as objects from the superclass (EscapeRoom)
        
        this.difficulty = 4; // this attribute (difficulty level) is automatically set to 4 when an object of this class is created
        this.shortDescription = "A magical place to handcraft potions and elixirs."; // this attribute (short description) is automatically set to this message when an object of this class is created
    } // end constructor PotionLabRoom()
    
    
    @Override
    /*
    this method displays a long description of the escape room, the description includes a short passage about how the room looks,
    the objective of the task in this room, and what you need to do to successfully complete the task
    */
    public void displayLongDescription(){
        // display the longer description of the escape room
        System.out.println("The Potion Lab is a mysterious place where all sorts of strange and wonderful\nbrews are kept. The shelves are lined with rows of glistening bottles and jars,\neach containing a different potion with its own unique properties.");
        
        // display the goal of the escape room
        System.out.println("\nGOAL:\nThere is a bubbling cauldron on a nearby table, your job is to make an\nAlgorithmic Accelerator Potion... a potion that speeds up one's ability\nto solve complex programming problems.");
        System.out.println("\nSelect the following 3 ingredients in the correct order to create the potion\nperfectly. Trick: you may use ingredients more than once, but it may not be\nright!? Coins will be awarded based on how accurately the potion is created.\n");
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
        
        // invoke the printFile method in the parent class and pass in the graphics text file as an argument - displays graphics for potion lab room
        printFile("GraphicsPotionRoom.txt");
        System.out.println("\n💡  Hint: The rainbow.\n"); // output a hint for the user in order to solve the task
        
        // declare and initialize an integer array to store the correct order of the ingredients - recipe  
        int[] potionRecipe = {2, 1, 3};
        // declare and initialize an integer array to store the users order of the ingredients when they make the potion/play the game
        int[] userPotionRecipe = new int[3];
        
        // declare and initialize a string array to store positions - used for display and formatting purposes
        String[] positions = {"first", "second", "third"};
        
        // declare and initialize a 2D string array to store the results for each player playing this room
        // each row represents a player and each column represents whether or not they put the correct ingredient at that step (3 steps to make potion)
        String[][] results = new String[this.playersPlaying.length][3];
        
        // GAME
        /*
        for loop, prompts the user to enter/choose an ingredient to mix into the cauldron for every step (3 steps in total so it loops 3 times)
        for every step the program ensures that the input was valid and within range
        */
        for(int i = 0; i < potionRecipe.length; i++){
            /*
            while loop prompts the user to choose ingredient and will loop until the user enters a valid number, 
            that is the input is both a number and within range of the number of ingredients presented. 
            If invalid input is entered, the user will be prompted again
            */
            while(true){
                // prompt the user to choose an ingredient 
                System.out.print("Enter the number of the " + positions[i] + " ingredient you want to add: ");
                /*
                try-catch block ensures that the user enters valid input (number) which is also within range
                catch block is executed if user enters input other than a number
                */
                try{
                    userPotionRecipe[i] = s.nextInt(); // get user input and store it at a specific index of the userPotionRecipe array
                    s.nextLine(); // fix glitch
                    
                    // if block ensures user enters a number within range, otherwise they will be prompted again
                    if(userPotionRecipe[i] < 1 || userPotionRecipe[i] > 3){
                        System.out.println("Invalid entry. Please try again.\n"); // output error message
                        continue; // goes back to the top of the loop to prompt the user to choose again 
                    }
                    break; // break out of while loop once user enters a valid answer
                }
                catch(InputMismatchException e){
                    System.out.println("Invalid input. Please enter a number.\n"); // print error message
                    s.nextLine(); // fix glitch
                }
            }
            System.out.println(""); // formatting purposes, prints new line
        } // end of for loop playing main game
        
        
        // CHECKING
        /*
        for loop, checks each of the players answers for every step/question and assigns a check mark or cross emoji respectivly
        
        first part of the loop checks the main player (user)
        second part of the loop is a nested loop and checks that specific step for each of the bot players
        */
        for(int i = 0; i < potionRecipe.length; i++){
            /*
            if-else block checks if the user entered the correct answer for a specific question, 
            compares the answer the user entered with the correct potion recipe
            
            if the user answered correctly a check mark emoji will be assigned to them for that question in the results array,
            and coins will be awarded based on the difficulty of the room
            
            otherwise, if the user answered incorrectly a cross will be assigned to them for that specific question in the results array
            */
            if(potionRecipe[i] == userPotionRecipe[i]){
                results[0][i] = "✔️ "; // assigns a checkmark if the user answered correctly to the array
                // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded in this room = 20, total max = 60
                this.playersPlaying[0].addCoins(this.difficulty*5); 
                
            }
            else{
                results[0][i] = "❌ "; // assigns a cross if the user answered incorrectly to the array
            }
            
            /*
            for loop, randomly generates answers for the bots and checks their answers, if they got it right or wrong
            */
            for(int j = 1; j < this.playersPlaying.length; j++){
                int randomPotion = (int)(Math.random()*3 + 1); // declare and initialize an integer variable to store a random number that counts as a valid answer within range
                /*
                if-else block checks if the bot player got the correct answer for a specific question, 
                compares the answer that was randomly generated with the correct potion recipe
                
                if the bot answered correctly a check mark emoji will be assigned to them for that question in the results array,
                and coins will be awarded based on the difficulty of the room
                
                otherwise, if the bot answered incorrectly a cross will be assigned to them for that specific question in the results array
                */
                if(potionRecipe[i] == randomPotion){
                    results[j][i] = "✔️ "; // assigns a checkmark if the bot answered correctly to the array
                    // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded in this room = 20, total max = 60
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
        System.out.println("\nAlgorithmic Accelerator - Potion Mastery Scorecard:\n"); // display formatting subheading message
        System.out.println("[  ORDER ][   PLAYER   ]"); // display formatting subheading message
        
        /*
        for loop outputs a scoreboard with all of the players and check mark or cross based on whether or not they got the step correct
        for loop loops as many times as there are players
        when displayed, each row represents the scoreboard for a specific player
        */
        for(int i = 0; i < results.length; i++){
            System.out.print("|"); // output a line for formatting purposes between each step 
            /*
            for loop goes through each question, since there are 3 steps it loops 3 times
            */
            for(int j = 0; j < 3; j++){
                System.out.print(results[i][j] + "|"); // output the emoji (checkmark/cross) and a line for formatting purposes between each step 
            }
            System.out.print(" - " + this.playersPlaying[i].getName() + "\n"); // display the player name of that score
        }
        
        System.out.println("\n💰  Coins were awarded based on Potion Mastery score.\n"); // output message to inform user that coins were awarded
    } // end playRoom()
    
} // end PotionLabRoom subclass