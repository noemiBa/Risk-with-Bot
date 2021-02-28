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
	  // methods for each turn are called here
    public MainTurn(ActivePlayer activePlayer, Game risk)
    {
        super();
        this.risk = risk;
    	this.window = risk.getWindow();
    	this.e = risk.getTurns().getE();
        reinforcementsAllocation(activePlayer);
        // other mainTurn methods go here
        // any method called after the attack method will need to have an if check to check if gameStage has been set to END
        window.clearText();
    }

    public void reinforcementsAllocation(ActivePlayer activePlayer) {
        int numberOfReinforcements = numberOfReinforcements(activePlayer);
        while (numberOfReinforcements != 0) {
            window.getTextDisplay(activePlayer.getName() + ", you have " + numberOfReinforcements + (numberOfReinforcements == 1 ? " reinforcement." : " reinforcements ")
                    + "to place. Please enter a country name belonging to you or a shortened version and the number of troops you want to allocate separated by space, " +
                     "then press enter");
            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            e.validateCountryAndUnitsEntered(input);
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


    public boolean isAdjacent(String countryA, String countryB) {
    	ArrayList<Country> countries = risk.getMap().getCountries(); 
    	boolean isAdjacent = false; 
    	
    	if (countries.get(Country.getIndex(countryA)).getAdjCountries().contains(Country.getIndex(countryB))) {
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
