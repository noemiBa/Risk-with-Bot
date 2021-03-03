package gamecomponents;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.botharmon.Game;

import gamecomponents.Turns.stage;
import lib.CustomArrayList;
import lib.ErrorHandler;
import lib.TextParser;
import player.ActivePlayer;
import ui.Window;

public class MainTurn extends Turns
{
	private Game risk;
	private Window window;
	private ErrorHandler e;
	private ActivePlayer activePlayer; 
	private ActivePlayer[] players; 
	
	  // methods for each turn are called here
    public MainTurn(ActivePlayer activePlayer, Game risk)
    {
        super();
        this.risk = risk;
    	this.window = risk.getWindow();
    	this.e = risk.getTurns().getE();
    	this.players = risk.getActivePlayers(); 
    	this.activePlayer = activePlayer;
    	
    	//mainTurn sequence
    	reinforcementsAllocation(activePlayer);
        attack(activePlayer);
        //need to check if the game has ended after each attack
        checkWinner(players);
        fortify(activePlayer);
        
        // other mainTurn methods go here
        window.clearText();
    }

    public void reinforcementsAllocation(ActivePlayer activePlayer) {
        int numberOfReinforcements = numberOfReinforcements(activePlayer);
        while (numberOfReinforcements != 0) {
            window.getTextDisplay(activePlayer.getName() + ", you have " + numberOfReinforcements + (numberOfReinforcements == 1 ? " reinforcement." : " reinforcements ")
                    + "to place. Please enter a country name belonging to you or a shortened version and the number of troops you want to allocate separated by a space, " +
                     "then press enter");
            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            
            input = e.validateCountryAndUnitsEntered(input);
                
            try {
            	countryName = TextParser.parse(input[0].trim());
            	activePlayer.getCountriesControlled().get(countryName);
            	numberToAdd = Integer.parseInt(input[1]);
            	if (numberToAdd > numberOfReinforcements || numberToAdd < 1) {
            		window.clearText();
            		window.sendErrorMessage("You can enter at most " + numberOfReinforcements + " for this allocation");
            	} else {
            		activePlayer.getCountriesControlled().get(countryName).setNumberOfUnits(activePlayer.getCountriesControlled()
            				.get(countryName).getNumberOfUnits() + numberToAdd);
            		numberOfReinforcements = numberOfReinforcements - numberToAdd;
            		window.updateMap();
            		window.clearText();
            	}
            } catch (IllegalArgumentException | NullPointerException e) {
            	window.sendErrorMessage("You entered the number or country name incorrectly");
            }
                System.out.println(numberOfReinforcements);
            }
        }
    

    public int numberOfReinforcements(ActivePlayer activePlayer) {
        //3 is the minimum by the rules
        if (activePlayer.getCountriesControlled().size() < 9) {
            return 3 + continentReinforcement(activePlayer);
        } else {
            return activePlayer.getCountriesControlled().size() / 3 + continentReinforcement(activePlayer);
        }
    }

    /* private helper method, return the extra troops if a player own a continent
     *
     * @param activePlayer: to call the custom array list of countries that the player owns
     */
    public int continentReinforcement(ActivePlayer player) {
        CustomArrayList<Country> countries = player.getCountriesControlled();

        //Use lambda to count if the continent name appear on the list as the same amount of the countries that the continent owns
        if (countries.stream().filter(s -> s.getContinentName().equals("Asia")).count() == 12) {
            return 7;
        } else if (countries.stream().filter(s -> s.getContinentName().equals("N America")).count() == 9) {
            return 5;
        } else if (countries.stream().filter(s -> s.getContinentName().equals("Europe")).count() == 7) {
            return 5;
        } else if (countries.stream().filter(s -> s.getContinentName().equals("Australia")).count() == 4) {
            return 2;
        } else if (countries.stream().filter(s -> s.getContinentName().equals("S America")).count() == 4) {
            return 2;
        } else if (countries.stream().filter(s -> s.getContinentName().equals("Africa")).count() == 6) {
            return 3;
        }
        return 0;
    }

    public void attack(ActivePlayer activePlayer) {
        String attackWith = "";
        String countryToAttack = "";

        window.getTextDisplay(activePlayer.getName() + ", enter the country you wish to attack with");
        attackWith = e.validateCountry(attackWith, activePlayer);
        attackWith = e.validateNumberOfUnits(attackWith, activePlayer);
        window.clearText();
        window.getTextDisplay(attackWith + " selected.");

        countryToAttack = e.validateAttack(activePlayer, activePlayer.getCountry(attackWith), countryToAttack);
        ArrayList <Integer> player1Rolls = new ArrayList<Integer>();
        ArrayList <Integer> player2Rolls = new ArrayList<Integer>();
    }
    
    // checkWinner can be called after each active player attack, this is the only time, the number of countries each player controls will change
    public void checkWinner(ActivePlayer[] activePlayers) {
        for (ActivePlayer a : activePlayers) {
            if (a.getCountriesControlled().size() == 42) {
                window.getTextDisplay("Congratulations " + a.getName() + " you conquered the world! Thanks for playing");
                setGameStage(stage.END);
            }
        }
    }
    
    public void fortify(ActivePlayer activePlayer) {
    	String countryOrigin = "";
    	String countryDestination = "";
        int unitsToMove = -1;
        
        window.clearText();
        window.getTextDisplay(activePlayer.getName() + ", enter the country to move units from, the destination country and the number of units to move, separated by a space.");
        window.getTextDisplay("Please, do not enter spaces between country names e.g. enter N Europe as Neurope");
        String[] input = window.getCommand().split("\\s+"); //splits the string between spaces
        
        e.validateCountriesAndUnitsEntered(input);
        
        try {
        countryOrigin = TextParser.parse(input[0].trim());
        countryDestination = TextParser.parse(input[1].trim());
        unitsToMove = Integer.parseInt(input[2]);
        
        System.out.println(isConnected(countryOrigin, countryDestination) + "to move " + unitsToMove);
        }
        catch (IllegalArgumentException e) {
        	e.printStackTrace(); 
        } 
    }
    public void test() { 
    	//activePlayer.setCountriesControlled(); 
    	window.getTextDisplay(activePlayer.getName() + " TEST TEST TEST Enter two country names separated by a space");
    	String[] input = window.getCommand().split("\\s+"); //splits the string between spaces
        
    	input[0] = TextParser.parse(input[0].trim());
        input[1] = TextParser.parse(input[1].trim());
       
        
    }
    
    public boolean isConnected(String countryOrigin, String countryDestination) {
    	CustomArrayList<Country> countries = risk.getMap().getCountries();
    	Queue<Country> queue = new LinkedList<Country>(); 
    	String newOrigin = ""; 
    	
    	queue.add(countries.get(countryOrigin)); 
    	
    	while (queue.size() >= 1) {
    	    
    		newOrigin = queue.remove().getName();
    		
    		//for each element removed from the queue, check whether it is the destination country
    		if (newOrigin.equals(countryDestination)) {
    			return true; 
    		}
    		
    		ArrayList<Integer> adjIndexes = countries.get(newOrigin).getAdjCountriesIndexes();
    		
    		for (Integer i : adjIndexes) {
    			if (controlledBySamePlayer(newOrigin, countries.get(i).getName())) {
    				//to avoid an infinite loop
    	    		if (countries.get(i).getName().equals(countryOrigin)) {
    	    			break; 
    	    		}
    				queue.add(countries.get(i));
    			}
    		}
    	}  
    	return false; 
    }
    
    public boolean isAdjacent(String countryA, String countryB) {
    	CustomArrayList<Country> countries = risk.getMap().getCountries();
    	boolean isAdjacent = false; 
    	
    	if (countries.get(countryA).getAdjCountriesIndexes().contains(Country.getIndex(countryB))) {
    		isAdjacent = true; 
    	}
    	return isAdjacent; 
    }
    
    public boolean controlledBySamePlayer(String countryA, String countryB) {
    	boolean controlledBySamePlayer = false; 
    	if (activePlayer.getCountriesControlled().get(countryA) != null && activePlayer.getCountriesControlled().get(countryB) != null) {
    		controlledBySamePlayer = true; 
    	}
    	
    	return controlledBySamePlayer; 
    }

}
