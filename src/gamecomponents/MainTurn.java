package gamecomponents;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import com.botharmon.Game;

import lib.CustomArrayList;
import lib.ErrorHandler;
import lib.TextParser;
import player.ActivePlayer;
import ui.Window;

public class MainTurn extends Turns {
    private Game risk;
    private Window window;
    private ErrorHandler e;
    private ActivePlayer activePlayer;
    private ActivePlayer[] players;

    // methods for each turn are called here
    public MainTurn(ActivePlayer activePlayer, Game risk) {
        super();
        this.risk = risk;
        this.window = risk.getWindow();
        this.e = risk.getTurns().getE();
        this.players = risk.getActivePlayers();
        this.activePlayer = activePlayer;

        //mainTurn sequence
        reinforcementsAllocation(activePlayer);
        //need to check if the game has ended after each attack
        checkWinner(players);
        attack(activePlayer);
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

                numberToAdd = e.validateNoUnitsReinforcement(numberToAdd, numberOfReinforcements);
                activePlayer.getCountriesControlled().get(countryName).setNumberOfUnits(activePlayer.getCountriesControlled()
                        .get(countryName).getNumberOfUnits() + numberToAdd);
                numberOfReinforcements = numberOfReinforcements - numberToAdd;
                window.updateMap();
                window.clearText();

            } catch (IllegalArgumentException | NullPointerException e) {
                window.sendErrorMessage("You entered the number or country name incorrectly");
            }
            System.out.println(numberOfReinforcements);
        }
    }


    public int numberOfReinforcements(ActivePlayer activePlayer) {
        //3 is the minimum by the rules
        if (activePlayer.getCountriesControlled().size() < 9) return 3 + continentReinforcement(activePlayer);
        else return activePlayer.getCountriesControlled().size() / 3 + continentReinforcement(activePlayer);
    }

    /* private helper method, return the extra troops if a player own a continent
     *
     * @param activePlayer: to call the custom array list of countries that the player owns
     */
    public int continentReinforcement(ActivePlayer player) {
        CustomArrayList<Country> countries = player.getCountriesControlled();

        //Use lambda to count if the continent name appear on the list as the same amount of the countries that the continent owns
        if (countries.stream().filter(s -> s.getContinentName().equals("Asia")).count() == 12) return 7;
        else if (countries.stream().filter(s -> s.getContinentName().equals("N America")).count() == 9) return 5;
        else if (countries.stream().filter(s -> s.getContinentName().equals("Europe")).count() == 7) return 5;
        else if (countries.stream().filter(s -> s.getContinentName().equals("Australia")).count() == 4) return 2;
        else if (countries.stream().filter(s -> s.getContinentName().equals("S America")).count() == 4) return 2;
        else if (countries.stream().filter(s -> s.getContinentName().equals("Africa")).count() == 6) return 3;

        return 0;
    }

    public void attack(ActivePlayer activePlayer) {
        String countryAttacking = "";
        String countryDefending = "";
        while (!countryAttacking.equals("skip")) {
            window.getTextDisplay(activePlayer.getName() + ", enter the country you wish to attack with or enter 'skip' to progress to the next stage");
            countryAttacking = e.validateAttackPhaseChoice(countryAttacking, activePlayer);
            if (!countryAttacking.equals("skip")) {
                countryAttacking = e.validateNumberOfUnitsAttacking(countryAttacking, activePlayer);
                window.clearText();
                window.getTextDisplay(countryAttacking + " selected.");
                countryDefending = e.validateAttack(activePlayer, activePlayer.getCountry(countryAttacking), countryDefending);
                window.clearText();

                ArrayList<Integer> activePlayerRolls = new ArrayList<Integer>();
                ArrayList<Integer> otherPlayerRolls = new ArrayList<Integer>();

                int numberOfAttacks, numberOfDefences;
                if (activePlayer.getCountry(countryAttacking).getNumberOfUnits() >= 4)
                    numberOfAttacks = 3;
                else
                    numberOfAttacks = activePlayer.getCountry(countryAttacking).getNumberOfUnits() - 1;
                if (risk.getMap().getCountry(countryDefending).getNumberOfUnits() >= 2)
                    numberOfDefences = 2;
                else
                    numberOfDefences = 1;

                for (int i = 1; i <= numberOfAttacks; i++)
                    activePlayerRolls.add(activePlayer.throwDice());

                for (int i = 1; i <= numberOfDefences; i++)
                    otherPlayerRolls.add(activePlayer.throwDice());

                activePlayerRolls.sort(Collections.reverseOrder()); // sort in descending order
                otherPlayerRolls.sort(Collections.reverseOrder());
                String otherPlayerName = risk.getMap().getCountry(countryDefending).getControlledBy().getName();

                window.getTextDisplay(activePlayer.getName() + (activePlayerRolls.size() == 1 ? " rolled a " + activePlayerRolls.get(0) : "'s rolls were " + activePlayerRolls));
                window.getTextDisplay(risk.getMap().getCountry(countryDefending).getControlledBy().getName() +
                        (otherPlayerRolls.size() == 1 ? " rolled a " + otherPlayerRolls.get(0) : "'s rolls were " + otherPlayerRolls));

                int successfulAttacks = 0, successfulDefends = 0;
                for (int i = 0, j = 0; i < numberOfAttacks && j < numberOfDefences; i++, j++) {
                    if (activePlayerRolls.get(i) > otherPlayerRolls.get(j)) {
                        successfulAttacks++;
                        risk.getMap().getCountry(countryDefending).setNumberOfUnits(risk.getMap().getCountry(countryDefending).getNumberOfUnits() - 1);
                        if (risk.getMap().getCountry(countryDefending).getNumberOfUnits() == 0) {
                            risk.getMap().getCountry(countryDefending).getControlledBy().getCountriesControlled().remove
                                    (risk.getMap().getCountry(countryDefending).getName(), risk.getMap().getCountry(countryDefending));
                            activePlayer.getCountriesControlled().add(risk.getMap().getCountry
                                    (countryDefending).getName(), risk.getMap().getCountry(countryDefending));
                            risk.getMap().getCountry(countryDefending).setControlledBy(activePlayer);
                            activePlayer.getCountry(countryDefending).setNumberOfUnits(activePlayer.getCountry(countryAttacking).getNumberOfUnits() - 1);
                            activePlayer.getCountry(countryAttacking).setNumberOfUnits(1);
                            break;
                        }
                    } else {
                        successfulDefends++;
                        activePlayer.getCountry(countryAttacking).setNumberOfUnits(activePlayer.getCountry(countryAttacking).getNumberOfUnits() - 1);
                    }

                }
                risk.getWindow().updateMap();
                risk.getWindow().getTextDisplay
                        (
                                activePlayer.getName() + " loses " + successfulDefends + (successfulDefends == 1 ? " unit" : " units") + " and " +
                                        otherPlayerName + " loses " + successfulAttacks + (successfulAttacks == 1 ? " unit" : " units")
                        );
                if (risk.getMap().getCountry(countryDefending).getControlledBy().equals(activePlayer))
                    risk.getWindow().getTextDisplay(activePlayer.getName() + " has conquered " + risk.getMap().getCountry(countryDefending).getName());
            }
            checkWinner(risk.getActivePlayers());
        }
    }

    public void fortify(ActivePlayer activePlayer) {
    	String countryOrigin = "";
    	String countryDestination = "";
    	int unitsToMove = -1;

    	window.clearText();
    	window.getTextDisplay(activePlayer.getName() + ", enter the country to move units from, the destination country and the number of units to move, separated by a space. \n\n"
    			+ "Simply enter 'skip' to progress to the next stage");
    	window.getTextDisplay("Please, do not enter spaces between country names e.g. enter N Europe as Neurope");
    	String inputStr = window.getCommand().toLowerCase();
    	if (!inputStr.equals("skip")) {
    		String[] input = inputStr.split("\"(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)\"|\\s+"); //splits the string between spaces

    		input = e.validateCountriesAndUnitsEnteredFortify(input);

    		if (!input[0].equals("skip")) {
    			try {
    				countryOrigin = TextParser.parse(input[0].trim());
    				countryDestination = TextParser.parse(input[1].trim());
    				unitsToMove = Integer.parseInt(input[2].trim());

    				if (isConnected(countryOrigin, countryDestination) == false) {
    					throw new IllegalArgumentException("The two countries are not connected");
    				}
    			}
    			//if the textParser cannot parse the country name, if the countries are not connected or if an integer was not entered, the input will be re-taken
    			catch (IllegalArgumentException ex) {
    				input = e.validateCountriesConnected(this, false);
    				countryOrigin = TextParser.parse(input[0].trim());
    				countryDestination = TextParser.parse(input[1].trim());
    				unitsToMove = Integer.parseInt(input[2].trim());
    			}

    			int unitsInCountryOrigin = activePlayer.getCountriesControlled().get(countryOrigin).getNumberOfUnits();
    			unitsToMove = e.validateNoUnitsFortify(unitsToMove, unitsInCountryOrigin);

    			activePlayer.getCountriesControlled().get(countryOrigin).setNumberOfUnits(unitsInCountryOrigin - unitsToMove);

    			int unitsInCountryDestination = activePlayer.getCountriesControlled().get(countryDestination).getNumberOfUnits();
    			activePlayer.getCountriesControlled().get(countryDestination).setNumberOfUnits(unitsInCountryDestination + unitsToMove);
    		}
    	}
//    	System.out.println(countryOrigin + " " +  countryDestination); 
//    	System.out.println(isConnected(countryOrigin, countryDestination) + " to move " + unitsToMove);
        window.updateMap();
        window.clearText();
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

    // checkWinner can be called after each active player attack, this is the only time, the number of countries each player controls will change
    public void checkWinner(ActivePlayer[] activePlayers) {
        for (int i = 0; i < activePlayers.length; i++) {
            if (activePlayers[i].getCountriesControlled().size() == 0) {
                if (i == 0) window.getTextDisplay("Congratulations " + activePlayers[1].getName() + " you are the winner of this game!");
                else window.getTextDisplay("Congratulations " + activePlayers[0].getName() + " you are the winner of this game!");

                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=KXw8CRapg7k&ab_channel=QueenVEVO"));
                    } catch (IOException | URISyntaxException ioException) {
                        ioException.printStackTrace();
                    }
                }
                setGameStage(stage.END);
            }
        }
    }
}
