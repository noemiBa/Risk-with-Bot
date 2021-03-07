package lib;

import com.botharmon.Game;

import gamecomponents.Country;
import gamecomponents.MainTurn;
import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;
import ui.Map;
import ui.Window;

import java.util.ArrayList;

public class ErrorHandler {
    Window window;

    public ErrorHandler(Window window) {
        this.window = window;
    }

    /*
     ****** GET PLAYER NAMES -  ERROR HANDLING
     * */

    /* method displays an error message if the player has entered an invalid input as their name.
     *
     * @param risk: the game currently being played
     * @param playerName: the input entered by the user
     * @return the valid playerName
     */
    public String validatePlayerName(String playerName) {
        while (playerName.trim().isEmpty() || playerName.matches("[0-9]+") || playerName.length() > 15) {
            window.sendErrorMessage("This name is too long or using incorrect characters, try again!");
            playerName = window.getCommand(); //send an error message and take and discard the input
        }
        return playerName;
    }

    /* method asks the second player to enter a different name than player one
     *
     * @param numberToAdd: array of active players, the input entered by the user and game instance
     * @return the valid player name
     */
    public String validateDifferentName(ActivePlayer[] activePlayers, String playerName, Game risk) {
        while (activePlayers[1].getName().equals(activePlayers[0].getName())) {
            window.getTextDisplay("Looks like you guys have the same name, what a coincidence! First Player arrived first, sorry!\n" +
                    "Second Player enter a new name: ");
            playerName = risk.getWindow().getCommand();
            risk.getActivePlayers()[1].setName(validatePlayerName(playerName));
        }
        return playerName;
    }


    /*
     ******* ALLOCATE UNITS - ERROR HANDLING
     * */

    public String[] validateCountryAndUnitsEntered(String[] input) {
        while (input.length != 2) {
            window.sendErrorMessage("You must enter a country name and a number");
            input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
        }
        return input;
    }

    /* displays an error message if the player has entered an invalid input as the number of Units to Add.
     *
     * @param numberToAdd: the input entered by the user
     * @return the valid numberToAdd
     */
    public int validateNoUnitsAllocation(int numberToAdd, int numberUnitsLeft) {
        while (numberToAdd > 3 || numberToAdd < 1 || numberUnitsLeft < numberToAdd) {
            window.sendErrorMessage("You can enter at most " + numberUnitsLeft + " for this allocation. Re-enter the number of units: ");
            String numberStr = window.getCommand();
            numberToAdd = isInteger(numberStr);
        }
        return numberToAdd;
    }

    /* displays an error message if the player has entered an invalid input (or ambiguous name) as country to add units to.
     *
     * @param countryName: the input entered by the user
     * @return the valid countryName
     */
    public String validateCountry(String countryName, Player player) {
        Country country = player.getCountriesControlled().get(countryName);

        while (countryName.trim().isEmpty()) {
            try {
                countryName = window.getCommand().trim();
                countryName = TextParser.parse(countryName);
                country = player.getCountriesControlled().get(countryName);

                while (country == null) {
                    window.sendErrorMessage("Sorry, it looks like " + player.getName() + " doesn't own this country");
                    countryName = window.getCommand().trim();
                    countryName = TextParser.parse(countryName);
                    country = player.getCountriesControlled().get(countryName);
                }

            } catch (IllegalArgumentException | NullPointerException e) {
                countryName = "";
                window.sendErrorMessage("Sorry, it looks like " + player.getName() + " you typed the name wrong."
                        + "\nEnter a country of " + player.getName() + "'s colour (and mind your spelling this time)");
            }
        }
        return country.getName();
    }

    /*
     ***** REINFORCEMENT STAGE - ERROR HANDLING
     * */

    /* displays an error message if the player has entered an invalid input as the number of Units to Add.
     *
     * @param numberToAdd: the input entered by the user
     * @return the valid numberToAdd
     */
    public int validateNoUnitsReinforcement(int numberToAdd, int numberUnitsLeft) {
        while (numberToAdd < 1 || numberUnitsLeft < numberToAdd) {
            window.sendErrorMessage("You can enter at most " + numberUnitsLeft + " for this allocation. Re-enter the number of units: ");
            String numberStr = window.getCommand();
            numberToAdd = isInteger(numberStr);
        }
        return numberToAdd;
    }

    /*
     ***** ATTACK STAGE - ERROR HANDLING
     * */
    public String validateAttackPhaseChoice(String choice, Player player) {
        boolean isInvalidCountry = true;
        while (isInvalidCountry) {
            choice = window.getCommand().replaceAll("\\s+", "").toLowerCase();
            if (choice.equals("skip"))
                return choice;
            try {
                choice = TextParser.parse(choice);
                player.getCountriesControlled().get(choice);
                isInvalidCountry = false;
            } catch (IllegalArgumentException | NullPointerException e) {
                window.sendErrorMessage("Sorry, it looks like " + player.getName() + " doesn't own this country or you typed it wrong!"
                        + "\nEnter a country of " + player.getName() + "'s colour");
            }
        }
        return choice;
    }


    public String validateNumberOfUnitsAttacking(String countryName, Player player) {
        countryName = TextParser.parse(countryName);

        if (player.getCountry(countryName).getNumberOfUnits() > 1) {
            return countryName;
        }

        while (player.getCountry(countryName).getNumberOfUnits() == 1) {
            window.sendErrorMessage("Sorry, it looks like " + countryName + " has insufficient units." +
                    "Enter a country of " + player.getName() + "'s colour with at least 2 units");
            countryName = window.getCommand().trim();
            countryName = TextParser.parse(countryName);

            while (player.getCountriesControlled().get(countryName) == null) {
                window.sendErrorMessage("Sorry, it looks like " + player.getName() + " doesn't own this country");
                countryName = window.getCommand().trim();
                countryName = TextParser.parse(countryName);
            }
        }
        return countryName;
    }

    public String validateAttack(ActivePlayer activePlayer, Country countryAttacking, String countryDefending) {
        boolean isInvalidAttack = true;
        while (isInvalidAttack) {
            window.getTextDisplay("You can attack the following countries: ");
            for (Country c : countryAttacking.getAdjCountries()) {
                if (!c.getControlledBy().equals(activePlayer)) {
                    window.getTextDisplay(c.getName());
                }
            }
            window.getTextDisplay("Enter a country to attack");
            countryDefending = window.getCommand().replaceAll("\\s+", "");
            try {
                countryDefending = TextParser.parse(countryDefending);
                if (!countryAttacking.getAdjCountry(countryDefending).getControlledBy().equals(activePlayer))
                    isInvalidAttack = false;
                else
                    window.sendErrorMessage("Sorry, it looks like the country you're attacking is controlled by you");
            } catch (IllegalArgumentException | NullPointerException e) {
                window.sendErrorMessage("Sorry, it looks like the country you entered is not adjacent to " + countryAttacking.getName() +
                        " or was entered incorrectly");
            }
        }
        return countryDefending;
    }

    /*
     ******* FORTIFY STAGE - ERROR HANDLING
     * */

    public int validateDicesDefend(Player player, String countryDefending) {
        int numberOfUnit = player.getCountriesControlled().get(countryDefending).getNumberOfUnits();
        int diceDefend = 0;
        if (player instanceof PassivePlayer) {
            if (numberOfUnit >= 2) {
                return 2;
            } else {
                return 1;
            }
        } else {
            window.getTextDisplay("You have " + numberOfUnit + " the number of dices to defend with: ");
            if (numberOfUnit >= 2) {
                window.getTextDisplay("You can defend with 1 or 2 dices, please enter the number you want to deffend: ");
                diceDefend = isInteger(window.getCommand());
                while (diceDefend != 1 || diceDefend != 2) {
                    diceDefend = isInteger(window.getCommand());
                }
                return diceDefend;
            } else {
                return 1;
            }
        }
    }

    public int validateDicesAttack(ActivePlayer activePlayer, String countryAttacking){
        int numberOfUnit = activePlayer.getCountriesControlled().get(countryAttacking).getNumberOfUnits();
        int diceAttack = 0;
        if (numberOfUnit == 2){
            return 1;
        } else if (numberOfUnit == 3){
            window.getTextDisplay("You can defend with 1 or 2 dices, please enter the number you want to defend: ");
            diceAttack = isInteger(window.getCommand());
            while (diceAttack != 1 || diceAttack != 2) {
                diceAttack = isInteger(window.getCommand());
            }
            return diceAttack;
        } else {
            window.getTextDisplay("You can defend with 1 or 2 dices, please enter the number you want to defend: ");
            diceAttack = isInteger(window.getCommand());
            while (diceAttack != 1 || diceAttack != 2 || diceAttack != 3) {
                diceAttack = isInteger(window.getCommand());
            }
            return diceAttack;
        }
    }

    public String[] validateCountriesAndUnitsEnteredFortify(String[] input) {
        while (input.length != 3) {
            window.sendErrorMessage("You must enter two country names and a number");
            input = window.getCommand().split("\"(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)\"|\\s+"); //splits the string between spaces

            if (input[0].equals("skip")) {
                input[0] = "skip";
                return input;
            }
        }
        return input;
    }

    public String[] validateCountriesConnected(MainTurn main, Boolean isConnected) {
        String[] input = new String[3];

        while (isConnected == false) {
            try {
                window.sendErrorMessage("That wasn't right! Please take this seriously. This is a war, afterall! \n\n"
                        + "Enter the country to move units from, the destination country and the number of units to move, separated by a space:");

                input = window.getCommand().split("\\s+"); //splits the string between spaces


                input = validateCountriesAndUnitsEnteredFortify(input);

                String countryOrigin = TextParser.parse(input[0].trim());
                String countryDestination = TextParser.parse(input[1].trim());
                String numberToMove = input[2].trim();

                if (!numberToMove.matches("[0-9]+")) {
                    window.sendErrorMessage("That wasn't an integer! ");
                    throw new IllegalArgumentException();
                }

                isConnected = main.isConnected(countryOrigin, countryDestination);
            } catch (IllegalArgumentException ex) {
                validateCountriesConnected(main, false);
            }
        }

        return input;
    }

    /* displays an error message if the player has entered an invalid input as the number of Units to Add.
     *
     * @param numberToAdd: the input entered by the user
     * @return the valid numberToAdd
     */
    public int validateNoUnitsFortify(int numberToMove, int numberUnitsLeft) {
        while (numberToMove < 1 || (numberUnitsLeft - 1) < numberToMove) {
            window.sendErrorMessage("You can enter at most " + (numberUnitsLeft - 1) + " for this allocation. Re-enter the number of units: ");
            String number = window.getCommand();
            numberToMove = isInteger(number);
        }
        return numberToMove;
    }

    public int isInteger(String numberStr) {
        while (!numberStr.matches("[0-9]+")) {
            window.sendErrorMessage("That wasn't an integer! Please, re-enter the number of units: ");
            numberStr = window.getCommand();
        }
        return Integer.parseInt(numberStr);
    }
}
