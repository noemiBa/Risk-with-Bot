package gamecomponents;

import java.util.ArrayList;

import com.botharmon.Game;

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
	
	  // methods for each turn are called here
    public MainTurn(ActivePlayer activePlayer, Game risk)
    {
        super();
        this.risk = risk;
    	this.window = risk.getWindow();
    	this.e = risk.getTurns().getE();
    	this.activePlayer = activePlayer; 
    	test();
        reinforcementsAllocation(activePlayer);
        attack(activePlayer);
        // other mainTurn methods go here
        // any method called after the attack method will need to have an if check to check if gameStage has been set to END
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
            input[0] = input[0].trim();
            if (input.length != 2) {
                window.clearText();
                window.sendErrorMessage("You must enter a country and a number");
            } else {
                try {
                    countryName = TextParser.parse(input[0]);
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
    
    public void test() { 
    	window.getTextDisplay(activePlayer.getName() + " TEST TEST TEST Enter two country names separated by a space");
    	String[] input = window.getCommand().split("\\s+"); //splits the string between spaces
        input[0] = TextParser.parse(input[0].trim());
        input[1] = TextParser.parse(input[1].trim());
        System.out.println(isConnected(input[0], input[1]));
    }
    
    public boolean isConnected(String countryOrigin, String countryDestination) {
    	
    	CustomArrayList<Country> countries = risk.getMap().getCountries();
    	ArrayList<Integer> adjIndexes = countries.get(Country.getIndex(countryOrigin)).getAdjCountriesIndexes();
    	if (isConnectedHelper(countryOrigin, countryDestination) == true) {
    		return true;
    	}
    	else {
    		for (int i = 0; i<adjIndexes.size(); i++) {
    			isAdjacent(countries.get(adjIndexes.get(i)).getName(), countryDestination);
    		}
    	}
    	return false; 
    }
    
    private boolean isConnectedHelper(String countryA, String countryB) {
    	if (isAdjacent(countryA, countryB) && controlledBySamePlayer(activePlayer, countryA, countryB)) {
    		return true; 
    	}
    	return false; 
    }
    
    public boolean isAdjacent(String countryA, String countryB) {
    	CustomArrayList<Country> countries = risk.getMap().getCountries();
    	boolean isAdjacent = false; 
    	
    	if (countries.get(Country.getIndex(countryA)).getAdjCountriesIndexes().contains(Country.getIndex(countryB))) {
    		isAdjacent = true; 
    	}
    	return isAdjacent; 
    }
    
    public boolean controlledBySamePlayer(ActivePlayer player, String countryA, String countryB) {
    	boolean controlledBySamePlayer = false; 
    	if (player.getCountriesControlled().get(countryA) != null && player.getCountriesControlled().get(countryB) != null) {
    		controlledBySamePlayer = true; 
    	}
    	
    	return controlledBySamePlayer; 
    }

}
