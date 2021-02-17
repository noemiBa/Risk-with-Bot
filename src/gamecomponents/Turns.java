package gamecomponents;

import com.botharmon.Game;

import lib.TextParser;
import player.ActivePlayer;
import player.PassivePlayer;
import ui.Map;
import ui.Window;

public class Turns
{
	Window window; 
    // Can be modified, feel free to change the ENUM values to add, remove, reorder stages of the game
    public enum stage
    {
        ENTER_NAMES,
        ASSIGN_COUNTRIES,
        ROLL_TO_PLACE_TERRITORIES,
        MAIN_TURN,
        END
    };

    private stage gameStage = stage.ENTER_NAMES;

    public stage getGameStage()
    {
        return gameStage;
    }
    
    public void setGameStage(stage gameStage) {
		this.gameStage = gameStage;
	}

    // Game ends when gameStage is set to stage.END
    public void nextTurns(Game risk)
    {
        while(gameStage != stage.END)
        {
            makeTurns(risk);
            // for testing purposes
            System.out.println(gameStage);
        }
    }

    // each players turn for each stage is implemented here
    public void makeTurns(Game risk)
    {	
    	window = risk.getWindow(); 
    	
        switch(gameStage)
        {
            case ENTER_NAMES:
                enterPlayerNames(risk);
                setGameStage(stage.ASSIGN_COUNTRIES);
                risk.getMap().updateUI(this.gameStage);
                break;
            case ASSIGN_COUNTRIES: 
            	 assignCountries(risk);
            	 setGameStage(stage.ROLL_TO_PLACE_TERRITORIES);
            	 break; 
            case ROLL_TO_PLACE_TERRITORIES:
                /*
                * may be better to implement player class as a circular ArrayList
                * could also switch player positions in the array to ensure the right player goes first
                */
                int playerWhoStarts = whoStarts(risk.getActivePlayers());
                allocateUnitsActivePlayers(risk.getActivePlayers(), playerWhoStarts, risk.getMap());
                //allocateUnitsPassivePlayers(risk.getPassivePlayers());
                setGameStage(stage.MAIN_TURN);
                break;
            case MAIN_TURN:
                // Main sequence of turns for both players can be implemented here
                // stage.END can be moved inside turn method with game winning condition to check if the game should end
            	setGameStage(stage.END);
                break;
            default:
                break;
        }
    }

	public void enterPlayerNames(Game risk)
    {
        for(ActivePlayer a: risk.getActivePlayers())
        {	//welcome the user and ask them to enter their names
            window.getTextDisplay("Welcome, player " + a.getPlayerNumber() + "! Enter your name: ");
            
            //take what the user types and put it in the string playerName
            String playerName = risk.getWindow().getCommand();
            
            //ensure the input entered by the user is valid
            a.setName(validatePlayerName(playerName));
        } 
       
        //Assign Players their colours
        window.getTextDisplay(risk.getActivePlayers()[0].getName() + " your color is Cyan");
        window.getTextDisplay(risk.getActivePlayers()[1].getName() + " your color is Pink");
    }
    
    public void assignCountries(Game risk) {
        Country.initialiseUnits(risk.getMap().getCountries());
        
        window.getTextDisplay(risk.getActivePlayers()[0].getName() + ", you drew the Countries: " + risk.getActivePlayers()[0].printCountries());
        window.getTextDisplay(risk.getActivePlayers()[1].getName() + ", you drew the Countries: " + risk.getActivePlayers()[1].printCountries());
        
        risk.getDeck().shuffle();
        window.getTextDisplay("Great, we are now ready to start the game!");
        
    }

    public int whoStarts(ActivePlayer[] activePlayers)
    {
        int diceFirstPlayer = activePlayers[0].throwDice();
        int diceSecondPlayer = activePlayers[1].throwDice();
        //inform the user 
        displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer); 
        		
        while(diceFirstPlayer == diceSecondPlayer) {	//if it's a draw
        	window.getTextDisplay("It was a draw, let's roll again"); 
        	displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer); 
            
        	diceFirstPlayer = activePlayers[0].throwDice();
            diceSecondPlayer = activePlayers[1].throwDice();
        }

        if(diceFirstPlayer > diceSecondPlayer)  {
            window.getTextDisplay(activePlayers[0].getName() + " starts!");
            return 0;
        }
        else  {
            window.getTextDisplay(activePlayers[1].getName() + " starts!");
            return 1;
        }
    }
    
    public void allocateUnitsActivePlayers(ActivePlayer[] activePlayers, int playerWhoStarts, Map map) {
    	int numberOfUnitsPlayer1 = 36; 
    	int numberOfUnitsPlayer2 = 36; 
    	
    	//print instructions
    	window.getTextDisplay("Allocate three units at a time until you've allocated all the 36 available units\n"
    			+ "To allocate a unit, type the name of the country (or a shortened version), followed by a space, and the number of units to allocate to it, then press enter");
    	
    	String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
    	
    	
    	input[0] = input[0].trim();
    	int numberToAdd = Integer.parseInt(input[1]); //NEED TO VALIDATE that it is actually an integer
    	String countryName = TextParser.parse(input[0]);
    	
    	//validate the user's input
    	numberToAdd = validateNoUnits(numberToAdd, 3); 
    	countryName = validateCountryName(countryName);
    	
    	activePlayers[playerWhoStarts].getCountriesControlled().get(countryName).setNumberOfUnits(activePlayers[playerWhoStarts].getCountriesControlled().get(countryName).getNumberOfUnits() + numberToAdd);
    	map.updateUI(); // updateUI can be moved to where appropriate part of the method, temporarily put here
    }
    
    public void allocateUnitsPassivePlayers(PassivePlayer[] passivePlayers) {
    	int numberOfUnits = 24; //the number of units each passive player starts with
    	//divide the units equally between the countries the passive players start with.
    	int numberOfUnitsPerCountry = numberOfUnits/GameData.INIT_COUNTRIES_NEUTRAL; 
    	
    	for(PassivePlayer p: passivePlayers) {
    		for (Country c : p.getCountriesControlled()) {
    			c.setNumberOfUnits(numberOfUnitsPerCountry);
    		}
    	}
    }
    
    /* private helper method, displays the what number the players have rolled
     * 
     * @param activePlayers: an array containing information regarding the two main players
     * @param diceOne, diceTwo: int representing the result of the die rolls
     */
    private void displayDiceRoll(ActivePlayer[] activePlayers, int diceOne, int diceTwo) {
    	window.getTextDisplay(activePlayers[0].getName() + " rolled a " + diceOne + "\n"
    	        + activePlayers[1].getName() + " rolled a " + diceTwo);
    }
    
    /* private helper method, displays an error message if the player has entered an invalid input as their name. 
     * 
     * @param risk: the game currently being played
     * @param playerName: the input entered by the user
     * @return the valid playerName
     */
    private String validatePlayerName(String playerName) {
    	//while the player name is an empty String or a number
    	while (playerName.trim().isEmpty() || playerName.matches("[0-9]+")) {
        	window.sendErrorMessage("That doesn't quite look like a name, try again!");
        	playerName = window.getCommand(); //send an error message and take and discard the input
        }
    	return playerName; 
    }
    
    /* private helper method, displays an error message if the player has entered an invalid input as the number of Units to Add. 
     * 
     * @param numberToAdd: the input entered by the user
     * @return the valid numberToAdd
     */
    private int validateNoUnits(int numberToAdd, int numberUnitsLeft) {
        while (numberToAdd > numberUnitsLeft) {
    		window.sendErrorMessage("You don't have that many units left I'm afraid, enter a new number");
    		numberToAdd = Integer.parseInt(window.getCommand());
    	}
        return numberToAdd;
    }
    
    /* private helper method, displays an error message if the player has entered an invalid input (or ambiguous name) as country to add units to. 
     * 
     * @param countryName: the input entered by the user
     * @return the valid countryName
     */
    private String validateCountryName(String countryName) {
        while (countryName.equals("Error")) {
        	System.out.println("here");
        	window.sendErrorMessage("We couldn't quite catch that, enter a new country name please");
        	countryName = TextParser.parse(window.getCommand().trim());
        }
        return countryName; 
    }
}
