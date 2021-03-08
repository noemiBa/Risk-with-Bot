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

import javax.swing.*;

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
        attack(activePlayer);
        if (risk.getTurns().getGameStage() != stage.END)
            fortify(activePlayer);
        window.clearText();
    }

    /* Method for the reinforcement stage of the game. It gives a minimum of three units to the user to allocate between countries controlled by them.
     * The number of units given to the user is number of countries owned divided by 3 (ignoring the decimal part)
     * If an user controls an entire continent, bonus units will be given.
     *
     * @param activePlayer: to call the custom array list of countries that the player owns
     */
    public void reinforcementsAllocation(ActivePlayer activePlayer) {
        int numberOfReinforcements = numberOfReinforcements(activePlayer);

        //while the user still has reinforcements to allocate...
        while (numberOfReinforcements != 0 && risk.getTurns().getGameStage() != stage.END) {
            window.getTextDisplay(activePlayer.getName() + ", you have " + numberOfReinforcements + (numberOfReinforcements == 1 ? " reinforcement." : " reinforcements ")
                    + "to place. Please enter a country name belonging to you or a shortened version and the number of troops you want to allocate separated by a space, " +
                    "then press enter");
            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits

            //ensure that both a country name and the number of units to be allocated to that country are entered.
            input = e.validateCountryAndUnitsEntered(input);

            try {
                countryName = TextParser.parse(input[0].trim());
                activePlayer.getCountriesControlled().get(countryName);
                numberToAdd = Integer.parseInt(input[1]);

                //ensure that the number of units entered by the user to be allocate is valid
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

    /* private helper method, return the number of troops for reinforcements to be given to the user.
     *
     * @param activePlayer: to call the custom array list of countries that the player owns
     */
    private int numberOfReinforcements(ActivePlayer activePlayer) {
        //3 is the minimum number of reinforcements according to the rules
        if (activePlayer.getCountriesControlled().size() < 9)
            return 3 + continentReinforcement(activePlayer);
        else
            return activePlayer.getCountriesControlled().size() / 3 + continentReinforcement(activePlayer);
    }

    /* private helper method, return the extra troops if a player own a continent
     *
     * @param activePlayer: to call the custom array list of countries that the player owns
     */
    private int continentReinforcement(ActivePlayer player) {
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

    /* Method for the attack stage of the game. It allows the user to attack and try to dominate an adjacent country - the user can keep attacking until he
     * still has units to attack with. The user cannot attack with a country containing a single unit.
     *
     * @param activePlayer: the player whose turn this is.
     */
    public void attack(ActivePlayer activePlayer) {
        //initialise the variables
        String[] attackChoice = {"", ""};
        String countryDefending = "";
        int numberOfAttacks, numberOfDefences;

        //while the user decides not to skip to the next stage...
        while (!attackChoice[0].equals("skip")) {
            window.getTextDisplay(activePlayer.getName() + ", enter the country you wish to attack with followed by a space and the number of units you wish to attack " +
                    "with or enter 'skip' to progress " + "to the next phase of your turn");

            //ensure that the user entered a valid country to attack with and a valid number of units.
            attackChoice = e.validateCountryAttacking(attackChoice, activePlayer);
            if (!attackChoice[0].equals("skip")) {
                numberOfAttacks = Integer.parseInt(attackChoice[1]);
                window.clearText();
                window.getTextDisplay(attackChoice[0] + " selected.");

                //ensure that the user has entered an adjacent country to attack 
                countryDefending = e.validateAttackChoice(activePlayer, activePlayer.getCountry(attackChoice[0]), countryDefending);
                window.clearText();

                ArrayList<Integer> activePlayerRolls = new ArrayList<Integer>();
                ArrayList<Integer> otherPlayerRolls = new ArrayList<Integer>();

                //if the country being attacked belongs to an active player, ask them how many dices to defend with.
                numberOfDefences = e.validateDicesDefend(risk.getMap().getCountries().get(countryDefending).getControlledBy(), countryDefending);

                //roll the dices for both users.
                for (int i = 1; i <= numberOfAttacks; i++)
                    activePlayerRolls.add(activePlayer.throwDice());

                for (int i = 1; i <= numberOfDefences; i++)
                    otherPlayerRolls.add(activePlayer.throwDice());

                activePlayerRolls.sort(Collections.reverseOrder()); // sort the dice rolls in descending order
                otherPlayerRolls.sort(Collections.reverseOrder());
                String otherPlayerName = risk.getMap().getCountry(countryDefending).getControlledBy().getName();

                //compare the highest roll with the highest roll and the second highest roll with the second highest roll...
                window.getTextDisplay(activePlayer.getName() + (activePlayerRolls.size() == 1 ? " rolled a " + activePlayerRolls.get(0) : "'s rolls were " + activePlayerRolls));
                window.getTextDisplay(risk.getMap().getCountry(countryDefending).getControlledBy().getName() +
                        (otherPlayerRolls.size() == 1 ? " rolled a " + otherPlayerRolls.get(0) : "'s rolls were " + otherPlayerRolls));

                int successfulAttacks = 0, successfulDefends = 0;
                for (int i = 0, j = 0; i < numberOfAttacks && j < numberOfDefences; i++, j++) {
                    if (activePlayerRolls.get(i) > otherPlayerRolls.get(j)) {
                        successfulAttacks++;
                        //update the map after each successful attack
                        risk.getMap().getCountry(countryDefending).setNumberOfUnits(risk.getMap().getCountry(countryDefending).getNumberOfUnits() - 1);
                        if (risk.getMap().getCountry(countryDefending).getNumberOfUnits() == 0) {
                            risk.getMap().getCountry(countryDefending).getControlledBy().getCountriesControlled().remove
                                    (risk.getMap().getCountry(countryDefending).getName(), risk.getMap().getCountry(countryDefending));
                            activePlayer.getCountriesControlled().add(risk.getMap().getCountry
                                    (countryDefending).getName(), risk.getMap().getCountry(countryDefending));
                            risk.getMap().getCountry(countryDefending).setControlledBy(activePlayer);
                            activePlayer.getCountry(countryDefending).setNumberOfUnits(numberOfAttacks);
                            activePlayer.getCountry(attackChoice[0]).setNumberOfUnits(activePlayer.getCountry(attackChoice[0]).getNumberOfUnits() - numberOfAttacks);
                            break;
                        }
                    } else {
                        successfulDefends++;
                        activePlayer.getCountry(attackChoice[0]).setNumberOfUnits(activePlayer.getCountry(attackChoice[0]).getNumberOfUnits() - 1);
                    }
                }
                risk.getWindow().updateMap();
                risk.getWindow().getTextDisplay
                        (        //inform the users of the outcome of the attack
                                activePlayer.getName() + " loses " + successfulDefends + (successfulDefends == 1 ? " unit" : " units") + " and " +
                                        otherPlayerName + " loses " + successfulAttacks + (successfulAttacks == 1 ? " unit" : " units")
                        );
                if (risk.getMap().getCountry(countryDefending).getControlledBy().equals(activePlayer))
                    risk.getWindow().getTextDisplay(activePlayer.getName() + " has conquered " + risk.getMap().getCountry(countryDefending).getName());
            }
            // check whether one of the players has won the game after each attack.
            checkWinner(risk.getActivePlayers());
        }
    }

    /* Method for the stage fortify, which allows the user to move units once from one country to another connected country.
     *
     * @param activePlayer: to call the custom array list of countries that the player owns and change the units
     */
    public void fortify(ActivePlayer activePlayer) {
        //initialise the variables used.
        String countryOrigin = "";
        String countryDestination = "";
        int unitsToMove = -1;

        window.clearText();
        window.getTextDisplay(activePlayer.getName() + ", enter the country to move units from, the destination country and the number of units to move, separated by a space. \n\n"
                + "Simply enter 'skip' to progress to the next stage");
        window.getTextDisplay("Please, do not enter spaces between country names e.g. enter N Europe as Neurope");
        String inputStr = window.getCommand().toLowerCase();
        //allow the user to skip the fortify phase if they so desire
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
                catch (IllegalArgumentException | NullPointerException ex) {
                    input = e.validateCountriesConnected(this, false);
                    countryOrigin = TextParser.parse(input[0].trim());
                    countryDestination = TextParser.parse(input[1].trim());
                    unitsToMove = Integer.parseInt(input[2].trim());
                }

                //Now that the input has been validated, decrement the units in the origin country and increment the units in the destination country
                int unitsInCountryOrigin = activePlayer.getCountriesControlled().get(countryOrigin).getNumberOfUnits();
                unitsToMove = e.validateNoUnitsFortify(unitsToMove, unitsInCountryOrigin);

                activePlayer.getCountriesControlled().get(countryOrigin).setNumberOfUnits(unitsInCountryOrigin - unitsToMove);

                int unitsInCountryDestination = activePlayer.getCountriesControlled().get(countryDestination).getNumberOfUnits();
                activePlayer.getCountriesControlled().get(countryDestination).setNumberOfUnits(unitsInCountryDestination + unitsToMove);
            }
        }

        window.updateMap();
        window.clearText();
    }

    /* public helper method, that checks if the origin country is connected to the destination country. Note: a country is connected to another if
     * there is a path of adjacent countries controlled by the same player in between them. Units cannot be moved between enemy territories.
     *
     * @param countryOrigin, countryDestination: name of the origin and destination countries
     */
    public boolean isConnected(String countryOrigin, String countryDestination) {
        CustomArrayList<Country> countries = risk.getMap().getCountries();
        Queue<Country> queue = new LinkedList<Country>();
        String newOrigin = "";
        //add the origin country to the queue 
        queue.add(countries.get(countryOrigin));

        while (queue.size() >= 1) {
            //for each element removed from the queue, check whether it is the destination country
            newOrigin = queue.remove().getName();

            if (newOrigin.equals(countryDestination)) {
                return true;
            }

            //find the countries adjacent to the new origin and, if they are controlled by the same player, add them to the queue 
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

        //if we reach this point, the queue is empty and the two countries are not connected.
        return false;
    }

    /* private helper method, that checks if the given countryA is controlled by the same player as countryB
     *
     * @param countryA, countryB: check if both countries given are controlled by the same player
     */
    private boolean controlledBySamePlayer(String countryA, String countryB) {
        boolean controlledBySamePlayer = false;
        try {
            if (activePlayer.getCountriesControlled().get(countryA) != null && activePlayer.getCountriesControlled().get(countryB) != null) {
                controlledBySamePlayer = true;
            }
        } catch (NullPointerException ex) {
            return controlledBySamePlayer = false;
        }
        ;

        return controlledBySamePlayer;
    }

    /* Method checks if any of the players has 0 troops, what means the other won
     *
     * @param activePlayers: array of active players to get the countries controlled by
     */
    public void checkWinner(ActivePlayer[] activePlayers) {
        for (int i = 0; i < activePlayers.length; i++) {
            if (activePlayers[i].getCountriesControlled().size() == 0) {
                risk.getTurns().setGameStage(stage.END);
                if (i == 0) {
                    int input = JOptionPane.showOptionDialog(null, "Congratulations " + activePlayers[1].getName() + " you are the winner!\nHit OK for you reward or CANCEL to exit the game!", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                    if (input == JOptionPane.OK_OPTION) {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            try {
                                Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=KXw8CRapg7k&ab_channel=QueenVEVO"));
                            } catch (IOException | URISyntaxException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    if (input == JOptionPane.OK_CANCEL_OPTION) {
                        System.exit(0);
                    }
                } else {
                    int input = JOptionPane.showOptionDialog(null, "Congratulations " + activePlayers[0].getName() + " you are the winner!\nHit OK for your reward or CANCEL to exit the game!", "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                    if (input == JOptionPane.OK_OPTION) {
                        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                            try {
                                Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=KXw8CRapg7k&ab_channel=QueenVEVO"));
                            } catch (IOException | URISyntaxException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    if (input == JOptionPane.OK_CANCEL_OPTION) {
                        System.exit(0);
                    }
                }
            }
        }
    }
}