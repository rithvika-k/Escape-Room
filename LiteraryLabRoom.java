import java.util.Scanner; // import Scanner class
import java.util.InputMismatchException; // import InputMismatchException class
import java.util.ArrayList; // import ArrayList class

// class LiteraryLabRoom, is a subclass of the abstract EscapeRoom class to inherit attributes and methods
public class LiteraryLabRoom extends EscapeRoom {
    
    /* constructor method, no new parameters are required compared to the constructor in the parent abstract class, 
    and the only parameters present are there to use the constructor from the super class, 
    
    this constructor will create a Literary Lab Room object, the difficulty and short description of the
    room is what makes it different compared to the constructor in the abstract parent class
    */
    public LiteraryLabRoom(String theName, Player[] thePlayersPlaying){
        super(theName, thePlayersPlaying); // the object will include the same attributes as objects from the superclass (EscapeRoom)
        
        this.difficulty = 10; // this attribute (difficulty level) is automatically set to 10 when an object of this class is created
        this.shortDescription = "A place to challenge your mind and unlock the secret mystery word."; // this attribute (short description) is automatically set to this message when an object of this class is created
    } // end constructor LiteraryLabRoom()
    
    
    @Override
    /*
    this method displays a long description of the escape room, the description includes a short passage about how the room looks,
    the objective of the task in this room, and what you need to do to successfully complete the task
    */
    public void displayLongDescription(){
        // display the longer description of the escape room
        System.out.println("The Literary Lab is a place where literature and the written word comes to life.\nIt's a room filled with all sorts of word puzzles and challenges, and it is up\nto you to analyze the words throughly.");
        
        // display the goal of the escape room
        System.out.println("\nGOAL:\nInside, a special book with tricky riddles is opened and you are required to\nuse your literary skills to solve the problems presented. However, each\nriddle holds importance and reveals a letter to the 4 letter secret word.");
        System.out.println("\nAnswer the following 4 riddles based on the options presented. Each riddle\nanswered correctly reveals one letter to the word.\n");
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
        printFile("GraphicsLiteraryRoom.txt");
        
        // declare and initialize a string array to hold the letters of the secret word
        String[] secretWord = {"w", "a", "n", "d"};
        
        // declare and initialize a 2D String array to hold 4 riddles, their options, and the correct answer
        // each row represents a riddle, column 1 are the riddles, column 2 are the options for each riddle, column 3 are the correct answers for each riddle
        String[][] riddles = {
          {"I have a tail and a head, but no body. What am I?", "1. A coin\n2. A vector", "1"},
          {"What gets wet as it dries?", "1. A towel\n2. A dog", "1"},
          {"If you don't keep me, I'll break. What am I?", "1. A promise\n2. Someone's trust", "1"},
          {"I go all around the world, but never leave the corner. What am I?", "1. A stamp\n2. A compass", "1"}
        };
        
        // declare and initialize a 2D string array to store the results for each player playing this room
        // each row represents a player and each column represents whether or not they put the correct answer for that riddle (4 riddles) and got the letter from the mystery word
        String[][] results = new String[this.playersPlaying.length][4];
        
        // create an ArrayList to store the letters from the secret word the user is able to reveal after answering the riddles
        ArrayList<String> userAnswerSecretWord = new ArrayList<String>();
        // for loop, loops 4 times and puts a blank underscore for each of the 4 elements since there are 4 letters in the secret word
        for(int i = 0; i < 4; i++){
            userAnswerSecretWord.add(" _ ");
        }
        
        // declare an integer variable to store the user's answer for every riddle
        int userAnswer;
        
        /*
        for loop, displays a riddle from the riddles array, displays the options and prompts the user
        to enter an answer, the user will be prompted until they enter a valid answer (a number) and an answer within range.
        answers will also be generated for the bot players for every riddle
        the program will then check which players got which riddles correct and assign data to the results array
        it will also reveal letters from the secret word for the main player accordingly
        */
        for(int i = 0; i < riddles.length; i++){
            // GAME
            // display subheading for visual appeal
            System.out.println("\n════════");
            System.out.println("RIDDLE " + (i+1));
            System.out.println("════════\n");
            
            // output the riddle and the its respectives options in multiple choice format
            System.out.println(riddles[i][0] + "\n" + riddles[i][1]); 
            
            /*
            while loop prompts the user to enter their answer and will loop until the user enters a valid number, 
            that is the input is both a number and within range of the number of options presented. 
            If invalid input is entered, the user will be prompted again
            */
            while(true){
                // prompt the user to enter their answer
                System.out.print("\nEnter your answer(#): ");
                /*
                try-catch block ensures that the user enters valid input (number) which is also within range
                catch block is executed if user enters input other than a number
                */
                try{
                    userAnswer = s.nextInt(); // get user input and store it in the userAnswer variable
                    s.nextLine(); // fix glitch
                    
                    // if block ensures user enters a number within range, otherwise they will be prompted again
                    if(userAnswer < 1 || userAnswer > 2){
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
            if-else block checks if the user entered the correct answer for a specific question, 
            compares the answer the user entered with the correct riddle answer
            
            if the user answered correctly a check mark emoji will be assigned to them for that question in the results array,
            and coins will be awarded based on the difficulty of the room
            
            otherwise, if the user answered incorrectly a cross will be assigned to them for that specific question in the results array
            */
            if(userAnswer == Integer.parseInt(riddles[i][2])){
                // inform the user that they got a letter revealed from the sceret word since they answered correctly
                System.out.println("\nLetter from secret word revealed: " + secretWord[i]);
                results[0][i] = "✔️ "; // assigns a checkmark if the user answered correctly to the array
                
                // updates a "_" in the secret word arraylist to a letter of the secretword depending on what riddle was answered
                userAnswerSecretWord.set(i, secretWord[i]); 
                
                // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded for one riddle = 50, total max = 200
                this.playersPlaying[0].addCoins(this.difficulty*5);
            }
            else{
                // inform the user that no letters were revealed since they answered incorrectly
                System.out.println("\nNo letters revealed.");
                results[0][i] = "❌ "; // assigns a cross if the user answered incorrectly to the array
            }
            
            /*
            for loop, randomly generates answers for the bots and checks their answers, if they got it right or wrong
            */
            for(int j = 1; j < this.playersPlaying.length; j++){
                Integer randomGuess = (int)(Math.random()*2 + 1); // declare and initialize an integer variable to store a random number that counts as a valid answer within range
                /*
                if-else block checks if the bot player got the correct answer for a specific question, 
                compares the answer that was randomly generated with the correct potion recipe
                
                if the bot answered correctly a check mark emoji will be assigned to them for that question in the results array,
                and coins will be awarded based on the difficulty of the room
                
                otherwise, if the bot answered incorrectly a cross will be assigned to them for that specific question in the results array
                */
                if(randomGuess == Integer.parseInt(riddles[i][2])){
                    results[j][i] = "✔️ "; // assigns a checkmark if the bot answered correctly to the array
                    // updates the players coins and adds coins based on the difficulty of the game. number of coins awarded for one riddle = 50, total max = 200
                    this.playersPlaying[j].addCoins(this.difficulty*5);
                }
                else{
                    results[j][i] = "❌ "; // assigns a cross if the bot answered incorrectly to the array
                    
                }
            }
        }
        
        // SCOREBOARD
        // prompt the user to press enter to see the results after playing the room
        System.out.print("\nPress enter to see results:"); 
        s.nextLine(); // allows user to press enter
        
        // display formatting subheading message
        System.out.println("\nLiterary Mastery Scorecard:\n");
        System.out.println("[  RIDDLES  ][   PLAYER   ]");
        System.out.println("[1 |2 |3 |4 ]");
        
        /*
        for loop outputs a scoreboard with all of the players and check mark or cross based on whether or not they got the riddle correct
        for loop loops as many times as there are players
        when displayed, each row represents the scoreboard for a specific player
        */
        for(int i = 0; i < results.length; i++){
            System.out.print("|");// output a line for formatting purposes between each step 
            /*
            for loop goes through each question, since there are 4 riddles it loops 4 times
            */
            for(int j = 0; j < 4; j++){
                System.out.print(results[i][j] + "|"); // output the emoji (checkmark/cross) and a line for formatting purposes between each riddle riddle score 
            }
            System.out.print(" - " + this.playersPlaying[i].getName() + "\n"); // display the player name of that score
        }
        
        System.out.println("\n💰  Coins were awarded based on Literary Mastery score.\n"); // output message to inform user that coins were awarded
        
        // display a message informing the user that the next outputted message are the letters they guessed 
        System.out.println(this.playersPlaying[0].getName() + ", here are the letters you guessed from the secret word.");
        System.out.print("Secret Word: \""); // subheading/message
        // for loop prints out each element in the userAnswerSecretWord ArrayList that holds the letters/blanks
        for(int i = 0; i < userAnswerSecretWord.size(); i++){
            System.out.print(userAnswerSecretWord.get(i)); // print out each element in the ArrayList secret word
        }
        System.out.print("\"\n\n"); // output two new lines for formatting purposes
        
    } // end playRoom()
    
} // end LiteraryLabRoom subclass