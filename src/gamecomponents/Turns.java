package gamecomponents;

import com.botharmon.Game;

import lib.TextParser;
import player.ActivePlayer;
import player.PassivePlayer;
import ui.Map;
import ui.Window;

import java.awt.*;

public class Turns {
    Window window;

    // Can be modified, feel free to change the ENUM values to add, remove, reorder stages of the game
    public enum stage {
        ENTER_NAMES,
        ASSIGN_COUNTRIES,
        ROLL_TO_PLACE_TERRITORIES,
        MAIN_TURN,
        END
    }

    ;

    private stage gameStage = stage.ENTER_NAMES;

    public stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(stage gameStage) {
        this.gameStage = gameStage;
    }

    // Game ends when gameStage is set to stage.END
    public void nextTurns(Game risk) {
        while (gameStage != stage.END) {
            makeTurns(risk);
            // for testing purposes
            System.out.println(gameStage);
        }
    }

    // each players turn for each stage is implemented here
    public void makeTurns(Game risk) {
        window = risk.getWindow();

        switch (gameStage) {
            case ENTER_NAMES:
                enterPlayerNames(risk);
                setGameStage(stage.ASSIGN_COUNTRIES);
                window.updatePlayerInfo(this.gameStage, risk.getActivePlayers(), risk.getPassivePlayers());
                break;
            case ASSIGN_COUNTRIES:
                assignCountries(risk);
                setGameStage(stage.ROLL_TO_PLACE_TERRITORIES);
                break;
            case ROLL_TO_PLACE_TERRITORIES:
                int playerWhoStarts = whoStarts(risk.getActivePlayers());
                allocateUnits(risk.getActivePlayers(), playerWhoStarts, risk.getMap(), risk);
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

    public void enterPlayerNames(Game risk) {
        for (ActivePlayer a : risk.getActivePlayers()) {    //welcome the user and ask them to enter their names
            window.getTextDisplay("Welcome, player " + a.getPlayerNumber() + "! Enter your name: ");

            //take what the user types and put it in the string playerName
            String playerName = risk.getWindow().getCommand();

            //ensure the input entered by the user is valid
            a.setName(validatePlayerName(playerName));
        }


        window.clearText();

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

    public int whoStarts(ActivePlayer[] activePlayers) {
        int diceFirstPlayer = activePlayers[0].throwDice();
        int diceSecondPlayer = activePlayers[1].throwDice();
        //inform the user 
        displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);

        while (diceFirstPlayer == diceSecondPlayer) {    //if it's a draw
            window.getTextDisplay("It was a draw, let's roll again");
            displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);

            diceFirstPlayer = activePlayers[0].throwDice();
            diceSecondPlayer = activePlayers[1].throwDice();
        }

        if (diceFirstPlayer > diceSecondPlayer) {
            window.getTextDisplay(activePlayers[0].getName() + " starts!");
            return 0;
        } else {
            window.getTextDisplay(activePlayers[1].getName() + " starts!");
            return 1;
        }
    }

    public void allocateUnits(ActivePlayer[] activePlayers, int firstToPlay, Map map, Game risk) {
        //print instructions
        int secondToPlay = 0;

        //changing value case is equal
        if (firstToPlay == 0) {
            secondToPlay = 1;
        }

        int count;

        //Nine rounds for each player to complete allocation
        for(count = 1; count <= 18; count++){
            if (count % 2 == 1) {
                allocateUnitsActivePlayers(activePlayers[firstToPlay], map, risk);
                allocateUnitsPassivePlayers(risk.getPassivePlayers(), activePlayers[firstToPlay], map);
            }
            if (count % 2 == 0) {
                allocateUnitsActivePlayers(activePlayers[secondToPlay], map, risk);
                allocateUnitsPassivePlayers(risk.getPassivePlayers(), activePlayers[secondToPlay], map);
            }
            System.out.println("round " + count);
        }
    }

    public void allocateUnitsActivePlayers(ActivePlayer activePlayers, Map map, Game risk) {
        int troops = 3;

        while (troops != 0) {
            window.getTextDisplay(activePlayers.getName() + ", you have " + troops + " in total to allocate. Please enter the country name or a short version and the number of troops you want to allocate separate by space, then press enter");

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            String countryName = TextParser.parse(input[0]);
            int numberToAdd = Integer.parseInt(input[1]); //NEED TO VALIDATE that it is actually an integer

            //validate the user's input
            numberToAdd = validateNoUnits(numberToAdd, troops);
            countryName = validateCountryName(countryName);

            activePlayers.getCountriesControlled().get(countryName).setNumberOfUnits
                    (activePlayers.getCountriesControlled().get(countryName).getNumberOfUnits() + numberToAdd);

            troops = troops - numberToAdd;

            System.out.println("Allocate " + numberToAdd + " to " + countryName); //For test purpose
            window.updateMap(); // updateUI can be moved to where appropriate part of the method, temporarily put here
            window.clearText();
        }
    }

    public void allocateUnitsPassivePlayers(PassivePlayer[] passivePlayers, ActivePlayer activePlayer, Map map) {
        //divide the units equally between the countries the passive players start with.
        window.getTextDisplay(activePlayer.getName() + " , now time to allocate the passive troops!\n");

        //Loop through each passive player and set the units with the given country name
        for (PassivePlayer p : passivePlayers) {
            window.getTextDisplay("Enter the country name of color " + getColorName(p));

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            String countryName = TextParser.parse(input[0]);
            p.getCountriesControlled().get(countryName).setNumberOfUnits
                    (p.getCountriesControlled().get(countryName).getNumberOfUnits() + 1);
            window.updateMap();
        }
        window.clearText();
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
        if (numberToAdd > 3 || numberToAdd < 1) {
            window.sendErrorMessage("You don't have that many units left I'm afraid, enter a new number");
            numberToAdd = Integer.parseInt(window.getCommand());
        }
       /* if (numberToAdd > numberUnitsLeft) {
            window.sendErrorMessage("You don't have that many units left I'm afraid, enter a new number");
            numberToAdd = Integer.parseInt(window.getCommand());
        }*/
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

    public String getColorName(PassivePlayer passivePlayer) {
        if (passivePlayer.getPlayerColor().equals(new Color(177, 212, 174))) {
            return "Pale Green";
        } else if (passivePlayer.getPlayerColor().equals(new Color(235, 232, 234))) {
            return "Light Grey";
        } else if (passivePlayer.getPlayerColor().equals(new Color(248, 250, 162))) {
            return "Pale Yellow";
        } else if (passivePlayer.getPlayerColor().equals(new Color(237, 181, 126))) {
            return "Peach";
        }
        return null;
    }
}
