// Rithvika Kathroju 
// 12/16/2022

import java.util.Scanner; // import Scanner class
import java.util.InputMismatchException; // import InputMismatchException class
import java.io.File; // import File class
import java.io.FileNotFoundException; // import FileNotFoundException class
import java.util.ArrayList; // import ArrayList class
import java.time.LocalDateTime; // import LocalDateTime class (to display date)
import java.time.Duration; // import Duration class (to calculate time spent in game)
import java.time.format.DateTimeFormatter; // import DateTimeFormatter class (format date)
import java.time.ZoneId; // import ZoneId class (convert date in GMT to EST)

public class MyProgram{
    
    // DECLARE GLOBAL VARIABLES
    static Scanner s = new Scanner(System.in); // declare and initialize Scanner object s for keyboard input
    
    static Player[] players = new Player[5]; // declare an array to store Player objects (5 players total)
    
    // declare and intialize an array to store the EscapeRoom objects (4 rooms total)
    // each object is constructed from a specific subclass of the abstract EscapeRoom class
    static EscapeRoom[] rooms = {
        new PotionLabRoom("Potion Lab", players),
        new ElectricalRoom("Electrical Room", players),
        new FishingRoom("Ice Fishing Room", players),
        new LiteraryLabRoom("Literary Lab", players)
    };
    
    static ArrayList<String> roomEmojis = new ArrayList<String>(); // declare an ArrayList of String type to store an emoji specific to each room
    
    // declare and initialize a counter to store number of rounds, equal to the rooms remaining. 
    // A room is eliminated from the rooms array once it has been chosen
    static int numberOfRounds = rooms.length; 
    
    // declare and intialize boolean variable to store whether the main user player is eliminated
    // in the case this is true, the game will end and the player with the most points in the last room will win
    static boolean mainPlayerIsEliminated = false; 
    
    // variables for time and duration 
    static LocalDateTime startDate; // declare and initalize a LocalDateTime object to store the date of the game once started
    static LocalDateTime endDate; // declare and initalize a LocalDateTime object to store the date of the game once ended 
    static String startDateStr; // declare String variable to store the string version of the start date (formatting purposes)
    static String endDateStr; // declare String variable to store the string version of the end date (formatting purposes)
    static Duration timeElapsed; // declare a Duration object to store the time it took for the user to complete the game
    static DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");  // declare and initialize DateTimeFormatter object to format dates in a specific way using the .ofPattern method
    
    
    // the main() 
    public static void main(String[] args){
        int escapeRoomIndex; // declare a variable to store the index of the escape room the user chooses every round
        
        roomEmojis.add("🔮  "); // add an emoji to the roomEmojis ArrayList for the Potion Lab 
        roomEmojis.add("⚠️  "); // add an emoji to the roomEmojis ArrayList for the Electrical Room 
        roomEmojis.add("🎣  "); // add an emoji to the roomEmojis ArrayList for the Ice Fishing Room
        roomEmojis.add("🔎  "); // add an emoji to the roomEmojis ArrayList for the Literary Lab 
        
        displayIntroduction(); // invoke method to display an introduction to the game, and display the formatted start date
        getPlayers(); // invoke method to get players for the game and add them to the players array of Player objects
        
        /*
        for loop loops based on the value of the numberOfRounds variable, it will only loop a maximum of 4 times, 
        since that is the maximum number of rooms in this game.
        
        for every round, the chooseEscapeRoom() method is invoked so that the user can pick an Escape Room from the options presented.
        The index of that chosen room is then stored, and then passed in as a parameter when the playEscapeRoom() method is invoked. 
        Once the chosen Escape Room is played, the displayPodiumAndEliminateLoser() method is invoked in order to display a podium 
        and eliminate the player with the least amount of coins (last player on podium)
        
        the if statement will check to see if the main player is eliminated, if true the program will break, otherwise the game will continue
        
        the program will not execute the for loop only when the main player is eliminated or when all of the rooms have been played in which
        the rooms array will have 0 elements and therefore the numberOfRounds variable will equal 0
        */
        for(int i = 0; i < numberOfRounds; i++){
            escapeRoomIndex = chooseEscapeRoom(); // invoke chooseEscapeRoom() function and store the returned value (chosen room index)
            playEscapeRoom(escapeRoomIndex); // invoke playEscapeRoom() method and pass in the index of the chosen room as an argument
            displayPodiumAndEliminateLoser(); // invoke displayPodiumAndEliminateLoser() method to display a podium of the players ranked from coins, and then eliminate the last player (player with least coins)
            // if block checks if main player is eliminated and will break out of the loop if true
            if(mainPlayerIsEliminated){
                break;
            }
        }
        
        /*
        if block checks if the main player is not eliminated, 
        if true that means that the for loop above executed completely and did not break, and therefore a win message will be displayed. 
        if false that means that the for loop above stopped executing because the main player was eliminated 
        */
        if(!mainPlayerIsEliminated){
            displayWinMessage(); // invoke displayWinMessage() method to display the win message for the main player
        }
    } // end main()
    
    
    /*
    this method displays the introduction message which includes what the game is about, the rules, objective and how to play
    the start date (the date/time of when the program begins) is recorded and stored, formatted and converted to EST Time Zone
    */
    public static void displayIntroduction(){
        printFile("Introduction.txt"); // print the the introduction text in the textfile by invoking the printFile method and passing it in as a parameter
        
        startDate = LocalDateTime.now(ZoneId.of("-05:00")); // initialize the start date variable to the current date (in EST) of when the game began.
        startDateStr = startDate.format(formatObj); // format the start date LocalDateTime object in a certain manner and convert it to a string, store it as a variable
        System.out.println("\n🕒  Starting Date/Time (EST): " + startDateStr); // display the formatted starting time of the game to the console
    } // end displayIntroduction()
    
    
    /*
    this method takes in a textfile as an argument and prints the text within it 
    */
    public static void printFile(String file){
        /*
        try/catch block checks to see if the file exists, if not the catch block would be executed
        */
        try{
            File theFile = new File(file); // create an object from the File class to store the file passed in
            Scanner f = new Scanner(theFile); // declare and initialize a scanner object to scan the text in the text file
            
            // the while loop executes so long as we have a line of text to read in.
            while(f.hasNextLine()){
                String line = f.nextLine(); // stores the line read by the scanner in a variable
                System.out.println(line); // prints the stored line
            }
            f.close(); // closes the scanner object for the file
        }
        catch(FileNotFoundException e){
            System.out.println("File does not exist"); // output error message
        }
    } // end printFile()
    
    
    /*
    this method gets the players for the game, each of the 5 players are intialized into the players array of Player objects
    it first prints an introduction, then initializes the first main player (the user), then intitializes the 4 remaining
    players as bot/computer players called "BaqMinion"
    */
    public static void getPlayers(){
        // output a subheading to the console to make it more organized and visually appealing
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("                                    PLAYERS");
        System.out.println("--------------------------------------------------------------------------------");
        
        System.out.print("👥  Lets get the players!\n\nEnter your name: "); // output a message, and prompt the user to enter their name 
        
        // create a Player object and store it in the players array (which is an array of objects) at index 0,
        // pass in a player name, and number of points
        players[0] = new Player(s.nextLine(), 0);
        
        
        /*
        for loop executes to create the 4 remaining Player objects (which are the computers), starting at index 1 of the players array
        */
        for(int i = 1; i < players.length; i++){
            // create a Player object and store it in the players array (which is an array of objects) at a specific index 
            // pass in a player name, and number of points
            players[i] = new Player("BaqMinion" + i, 0);
        }
        
        System.out.println("\nHere is a list of all the players:"); // output message to inform the user that a list of the players will be displayed
        
        /*
        for loop will go through each Player object in the players array and print out their name in a list format
        since every object from the Player class has access to the methods within the class, the getName() method is used to retrive the private name attribute of that specific object
        */
        for(int i = 0; i < players.length; i++){
            System.out.println("- " + players[i].getName()); // output player name (used concatenation)
        }
    } // end getPlayers()
    
    
    /*
    this method (function) prompts the main player (user) to choose an escape room, and then returns the index of the chosen room
    the method first displays the 4 options (Potion Lab, Electrical Room, Ice Fishing Room, Literary Lab) including the room's short description and difficulty 
    the user is then prompted to pick a room, while also ensuring that the room exisits and is valid
    */
    public static int chooseEscapeRoom(){
        int roomChoice; // declare a variable to store the user's room choice from the Escape Rooms presented
        
        // output a subheading to the console to make it more organized and visually appealing
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("                                  ESCAPE ROOMS");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("\nHere are the escape rooms.\n"); // display a message informing the user that the room choices will be presented
        
        /*
        for loop goes through each room in the rooms array of Escape Room objects and will print out the details accordingly, for each room 
        the for loop will display the name of the room, the level (difficulty) of the room as well as the short description in well formatted manner
        in addition emojis from the roomEmojis arraylist will be used accordingly to make it more visually appealing 
        */
        for(int i = 0; i < rooms.length; i++){
            System.out.println((i+1) + ". " + roomEmojis.get(i) + rooms[i].getName() + " |🔶  Level " + rooms[i].getDifficulty() + "\n      - " + rooms[i].getShortDescription() + "\n"); // output name, difficulty, and description in formatted manner
        }
        
        /*
        while loop prompts the user to choose an escape room and will loop until the user enters a valid number, 
        that is the input is both a number and within range of the number of escape rooms. 
        If invalid input is entered, the user will be prompted again
        */
        while(true){
            System.out.print("\nChoose an escape room (#): "); // prompt the user to choose an escape room from the option presented above
            
            /*
            try-catch block ensures that the user enters valid input (number) which is also within range
            catch block is executed if user enters input other than a number
            */
            try{
                roomChoice = s.nextInt(); // get user input (their choice) and store it in the variable 
                s.nextLine(); // fix glitch
                
                // if block ensures user enters a number within range, otherwise they will be prompted again
                if(roomChoice < 1 || roomChoice > rooms.length){
                    System.out.println("Invalid entry. Please try again."); // output error message
                    continue; // goes back to the top of the loop to prompt the user to choose again 
                }
                
                System.out.println(""); // formatting purposes, prints a new line
                return (roomChoice-1); // the method will return the index of the chosen room
            }
            // executed if user does not enter a number
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number."); // print error message
                s.nextLine(); // fix glitch
            }
        }
    } // end chooseEscapeRoom()
    
    
    /*
    this method plays the chosen escape room and takes in the room index as a parameter to do so.
    
    it will create a new EscapeRoom object for the chosen room, then proceed to remove the chosen room 
    from the rooms array (array of all the EscapeRoom objects). 
    
    the method will then update the players array since after every round/room played the player with the least
    coins is elimimated. 
    
    A welcome message and long description about the room is displayed, and then the room is played (using methods from the EscapeRoom abstract class)
    */
    public static void playEscapeRoom(int roomIndex){
        // declare and intialize an EscapeRoom object to store the chosen room to play
        EscapeRoom chosenRoom = rooms[roomIndex]; 
        
        // invoke the deleteRoomFromArray method to remove the chosen room since it has been selected, this way the user can only pick each room once (cannot pick a room once selected)
        rooms = deleteRoomFromArray(roomIndex, rooms); 
        
        // using the setPlayers method the abstract EscapeRoom class to update the players playing before playing chosen room since the player with least coins after every round is eliminated
        chosenRoom.setPlayersPlaying(players); 
        
        // output border for formatting/visual purposes
        System.out.println("\n--------------------------------------------------------------------------------\n"); 
        // output a welcome message informing the user that they entered the room
        // used method chaining (invoking multiple method calls) and used the roomEmojis ArrayList
        System.out.println(roomEmojis.get(roomIndex) + ("Welcome to the " + chosenRoom.getName()).toUpperCase() + roomEmojis.get(roomIndex) + "\n"); 
        
        // since the room is removed after being chosen, the emoji associated with the room should also be removed after being used, otherwise it would interfere with the way you refer to a an element using indicies 
        roomEmojis.remove(roomIndex);
        
        // display the description of the chosen room by calling the displayLongDescription() method
        chosenRoom.displayLongDescription();
        // play the game of the chosen room by calling the playRoom() method
        chosenRoom.playRoom();
    } // end playEscapeRoom()
    
    
    /*
    this method takes in the room index to delete and roomArray of EscapeRoom objects as parameters
    
    deletes a EscapeRoom object from an array of EscapeRoom objects, and then returns the new rooms array
    this method is mainly used to a eliminate the room after it has been chosen (cannot play a room more than once)
    */
    public static EscapeRoom[] deleteRoomFromArray(int roomIndexToDelete, EscapeRoom[] roomArray){
        // create a newRoomArray that has one less element compared to the roomArray passed in 
        EscapeRoom[] newRoomArray = new EscapeRoom[roomArray.length-1];
        
        /*
        for loop copies each of the old elements into the new array excluding the room to delete
        */
        for(int i = 0; i < roomIndexToDelete; i++){
            newRoomArray[i] = roomArray[i];
        }
        
        /*
        for loop continues from right after the room to delete, and copies each of the old elements into the new array
        this way, it excludes the room that the program wants to delete in the new array 
        */
        for(int i = roomIndexToDelete; i < newRoomArray.length; i++){
            newRoomArray[i] = roomArray[i+1];
        }
        
        return newRoomArray; // the method will return the newRoomArray of EscapeRoom objects
    } // end deleteRoomFromArray()
    
    
    /*
    this method displays the podium of the players in terms of the number of coins they have, and 
    eliminates the loser/the player with the least amount of coins. If the main player (user) is eliminated 
    then the game will end and the player with the most coins in the room last played will win.
    
    */
    public static void displayPodiumAndEliminateLoser(){
        // declare and initialize array to store the original index of the players in order of greatest to least coins (index from the array of players objects)
        // indicies are in decreasing order of coins
        int[] orderedListOfPlayerIndicies = new int[players.length];
        
        // declare and initialize array to store the number of coins of each player
        int[] playerCoins = new int[players.length];
        
        // declare and initialize new player list of Player objects, will store the list of players in order of greatest coins to least coins
        Player[] newPlayerList = new Player[players.length];
        
        /*
        for loop gets the numbers of coins of each player (in order of the players array) and adds it to the playerCoins array
        */
        for(int i = 0; i < players.length; i++){
            playerCoins[i] = players[i].getCoinsCollected(); // gets the number of coins a player has and adds it to the playerCoins array
        }
        
        /*
        for loop, loops as many times as there are player in order to find the ordered list of indicies of the players based on number of coins (most to least)
        */
        for(int i = 0; i < players.length; i++){
            // declare and initalize a variable to store the index of the player with the greatest coins, set to 0 by default
            int maxIndex = 0;
            
            /*
            for loop compares the (number of coins of each player after the first player) with (the player with the most coins)
            if (any player after the first player) has more coins than the (player with the most coins) then maxIndex (which
            stores the index of the player with the most coins) will equal the index of the player that has the most coins 
            
            this continues until it goes through all of the players 
            */
            for(int j = 1; j < players.length; j++){
                if(playerCoins[j] > playerCoins[maxIndex]){
                    maxIndex = j;
                }
            }
            // this will store the index of the player with the greatest coins
            orderedListOfPlayerIndicies[i] = maxIndex;
            // that same player with greatest coins will now have their number of points = -1, so that we can find the player with the next greatest number of points
            playerCoins[maxIndex] = -1;
        }
        
        /*
        for loop creates a list of players in the sorted order of decreasing coins
        */
        for(int i = 0; i < players.length; i++){
            newPlayerList[i] = players[orderedListOfPlayerIndicies[i]]; // initialize newPlayerList to hold the sorted order of players in terms of coins
        }
        
        // output a subheading for formatting purposes and to make the game visually appealing
        System.out.println("════════════════");
        System.out.println("Updated Podium:");
        System.out.println("════════════════");
        
        /*
        for loop goes through each player object in the newPlayerList, and outputs details regarding that player
        outputs the place they ranked, their name, and the number of coins they have in total
        */
        for(int i = 0; i < newPlayerList.length; i++){
            System.out.println((i+1) + ". " + newPlayerList[i].getName() + " | 💰  " + newPlayerList[i].getCoinsCollected());
        }
        
        Player leastCoinsPlayer = newPlayerList[newPlayerList.length-1]; // declare and intitalize a Player object to store the player with the least coins
        Player mostCoinsPlayer = newPlayerList[0]; // declare and initialize a Player object to store the player with the most coins
        
        /*
        for loop goes through each player in the players array of Player objects 
        
        it then compares each player object to the leastCoinsPlayer object...
        if they are the same the player will be eliminated and removed from the players array, which is done by invoking the deletePlayerFromArray method
        
        a nested if-block checks if the main player got eliminated since the main player (user) is at index 0 of the players array, 
        if true then the end date of the game will be generated and a game over message will be displayed
        */
        for(int i = 0; i < players.length; i++){
            /*
            if block compares the player with the leastCoinsPlayer, if they are the same the player will be eliminated (removed from players array)
            */
            if(players[i] == leastCoinsPlayer){
                System.out.println("\n❗❗ " + players[i].getName() + " has been eliminated for being last on the podium."); // display message to inform that a player has been eliminated
                players = deletePlayerFromArray(i, players); // remove the player by invoking the deletePlayerFromArray() and passing in the index of the player and the array to delete from
                
                /*
                nested if-block will execute if the player that was eliminated was the main player (user) in which the game will end
                */
                if(i == 0){
                    mainPlayerIsEliminated = true; // change boolean variable of whether or not the main player is eliminated to true
                    endDate = LocalDateTime.now(ZoneId.of("-05:00")); // generated and intialize the end date of the game (in EST)
                    endDateStr = endDate.format(formatObj); // format the end date LocalDateTime object in a certain manner and convert it to a string, store it as a variable
                    
                    // output game over message, declare that the main player lost and that some other player won
                    System.out.println("\n--------------------------------------------------------------------------------\n");
                    System.out.println("Sadly, you lost the game. This means that...");
                    System.out.println("\n🏆  Congrats to " + mostCoinsPlayer.getName() + ", they won the Escape Rooms Adventure Game!");
                    System.out.println("\n🕒  Ending Date/Time (EST): " + endDateStr); // output end date 
                    
                    displayTimeElapsed(startDate, endDate); // invoke displayTimeElapsed() method and pass in the start date and end date as arguments to output the duration/how long it took to complete the program
                    
                    System.out.println("\nGAME OVER");
                    System.out.println("\n--------------------------------------------------------------------------------");
                }
            }
        }
    } // end displayPodiumAndEliminateLoser()
    
    
    /*
    this method takes in the player index to delete and playerArray of Player objects as parameters
    
    deletes a Player object from an array of Player objects, and then returns the new players array
    this method is mainly used to a eliminate the player with the least coins after a room is played
    */
    public static Player[] deletePlayerFromArray(int playerIndexToDelete, Player[] playerArray){
        // create a newPlayerArray that has one less element compared to the playerArray passed in 
        Player[] newPlayerArray = new Player[playerArray.length - 1];
        
        /*
        for loop copies each of the old elements into the new array excluding the player to delete
        */
        for(int i = 0; i < playerIndexToDelete; i++){
            newPlayerArray[i] = playerArray[i];
        }
        
        /*
        for loop continues from right after the player to delete, and copies each of the old elements into the new array
        this way, it excludes the player that the program wants to delete in the new array 
        */
        for(int i = playerIndexToDelete; i < newPlayerArray.length; i++){
            newPlayerArray[i] = playerArray[i+1];
        }
        
        return newPlayerArray; // the method will return the newPlayerArray of Player objects
    } // end deletePlayerFromArray()
    
    /*
    this method displays a win message and is executed if the main player(user) wins
    it also generates and displays the end date of when the game/program finished
    */
    public static void displayWinMessage(){
        endDate = LocalDateTime.now(ZoneId.of("-05:00")); // generated and intialize the end date of the game (in EST)
        endDateStr = endDate.format(formatObj); // format the end date LocalDateTime object in a certain manner and convert it to a string, store it as a variable
        
        // output game end message since the main player won, and display end date of game
        System.out.println("\n--------------------------------------------------------------------------------\n");
        System.out.println("🏆  Congrats " + players[0].getName() + ", you won the Escape Rooms Adventure Game!\n   You are the player with the most coins!");
        System.out.println("\n🕒  Ending Date/Time (EST): " + endDateStr); // output end date 
        
        displayTimeElapsed(startDate, endDate); // invoke displayTimeElapsed() method and pass in the start date and end date as arguments to output the duration/how long it took to complete the program
        
        System.out.println("\nTHE END");
        System.out.println("\n--------------------------------------------------------------------------------");
    } // end displayWinMessage()
    
    
    /*
    this method displays the time elapsed, comparing the start date and end date (Calculates duration/how long the user spent to finish the escape rooms)
    */
    public static void displayTimeElapsed(LocalDateTime startTime, LocalDateTime endTime){
        timeElapsed = Duration.between(startTime, endTime); // using the Duration class and the between() calculate the time elapsed from start to end
        
        long seconds = timeElapsed.getSeconds(); // declare and initialize a long variable to store the time elapsed in seconds 
        
        long secondsRemaining = seconds % 60; // declare and intialize a variable to store the seconds remaining (ex. since 60 sec = 1 min, 65 sec = 1 min and 5 sec ... this variable will store the 5 sec part)
        long minutesRemaining = ((seconds-secondsRemaining)/60) % 60; // declare and intialize a variable to store the minutes remaining (ex. since 60 min = 1 hour, 65 min = 1 hour and 5 min ... this variable will store the 5 min part)
        long hoursRemaining = ((seconds-secondsRemaining-(minutesRemaining*60))/3600); // declare and intialize a variable to store the number of hours
        
        System.out.println("⌛  Time spent in Escape Rooms: " + hoursRemaining + " hour(s), " + minutesRemaining + " minute(s), and " + secondsRemaining + " second(s)."); // display the duration/time spent in a formatted manner
    } // end displayTimeElapsed()
    
} // end MyProgram
