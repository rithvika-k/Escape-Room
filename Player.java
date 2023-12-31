// class Player
public class Player {
    
    // declare instance fields (attributes) for objects of this class
    // two attributes for a Player object (name and number of coins)
    private String name;
    private int coinsCollected;
    
    // constructor method, has the room name as a String parameter, and the players playing as an array of Player objects parameter
    public Player(String theName, int theCoinsCollected){
        // set the values for each of the instance fields/attributes based on the arguments passed in
        this.name = theName;
        this.coinsCollected = theCoinsCollected;
    } // end constructor Player()
    
    // this method gets the name of the Player object and returns it
    public String getName(){
        return this.name; // returns name of the Player object
    } // end getName()
    
    // this method gets the number of coins of the Player object and returns it
    public int getCoinsCollected(){
        return this.coinsCollected;// returns number of coins of the Player object
    } // end getCoinsCollected()
    
    // this method adds a specific amount of coins to the Player object, takes in the coins to add as an int parameter
    public void addCoins(int coinsToAdd){
        this.coinsCollected += coinsToAdd; // adds the coins to the Player object
    } // end addCoins()
    
} // end Player class