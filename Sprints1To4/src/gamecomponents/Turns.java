package gamecomponents;

import com.botharmon.Game;
import lib.ErrorHandler;
import lib.TextParser;
import player.ActivePlayer;
import player.PassivePlayer;
import ui.Window;

import java.awt.*;

public class Turns {
    private Window window;
    private static int currentPlayer = 0;
    private ErrorHandler e;

    public enum stage {
        ENTER_NAMES,
        ASSIGN_COUNTRIES,
        ROLL_TO_PLACE_TERRITORIES,
        MAIN_TURN,
        END
    }

    //set the initial stage as enter names.
    private stage gameStage = stage.ENTER_NAMES;

    /*ACCESSOR METHODS*/
    public stage getGameStage() {
        return gameStage;
    }

    public ErrorHandler getE()
    {
        return e;
    }

    /*MUTATOR METHODS*/
    public void setWindow(Window window) {
        this.window = window;
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

    /* Method calls the methods necessary for each of the game stages, then proceeds to set the game stage to the next game stage.
     *
     * @param risk, the game of risk currently being played.
     */
    public void makeTurns(Game risk) {
        setWindow(risk.getWindow());
        e = new ErrorHandler(risk.getWindow());

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
                currentPlayer = whoStarts(risk.getActivePlayers());
                allocateUnits(risk.getActivePlayers(), currentPlayer, risk);
                currentPlayer = whoStarts(risk.getActivePlayers());
                // welcome message is printed here as you don't want it to be printed on each turn, can be moved to separate method if needs be
                window.getTextDisplay("You start " + risk.getActivePlayers()[currentPlayer].getName() +
                        "! Each turn consists of placing reinforcements, attacking, drawing a card and reallocating units");
                setGameStage(stage.MAIN_TURN);
                break;
            case MAIN_TURN:
                MainTurn main = new MainTurn(risk.getActivePlayers()[currentPlayer], risk);
                currentPlayer = switchTurn();
                break;
            default:
                break;
        }
    }

    /* Method takes the player names entered by the users to initialise the game.
     *
     * @param risk, the game of risk currently being played.
     */
    public void enterPlayerNames(Game risk) {
        //welcome the user and ask them to enter their names
        String playerName = null;
        for (int i = 0; i < risk.getActivePlayers().length; i++)
        {
            window.getTextDisplay("Welcome, player " + risk.getActivePlayers()[i].getPlayerNumber() + "! Enter your name: ");
            playerName = risk.getWindow().getCommand();
            risk.getActivePlayers()[i].setName(e.validatePlayerName(playerName));
            //ensure the players do not enter the same names
            e.validateDifferentName(risk.getActivePlayers(), playerName, risk);
            window.clearText();
        }

        window.clearText();
    }

    /* Method displays the countries randomly drawn by the active players, then proceeds to reshuffle the deck of cards.
     *
     * @param risk, the game of risk currently being played.
     */
    public void assignCountries(Game risk) {
        Country.initialiseUnits(risk.getMap().getCountries());
        window.getTextDisplay(risk.getActivePlayers()[0].getName() + ", you drew the Countries: " + risk.getActivePlayers()[0].printCountries());
        window.getTextDisplay(risk.getActivePlayers()[1].getName() + ", you drew the Countries: " + risk.getActivePlayers()[1].printCountries());
        risk.getDeck().shuffle();
    }

    /* Method allocates the units for active and passive players before the beginning of the main turn. (36 units for each active player and 24 units for
     * each passive player)
     *
     * @param risk, the game of risk currently being played.
     * @param firstToPlay: the player who won the first dice roll to begin the first game
     * @param activePlayers: the array of activePlayers
     */
    public void allocateUnits(ActivePlayer[] activePlayers, int firstToPlay, Game risk) {
        //print instructions
        int secondToPlay = 0;

        //changing value case is equal
        if (firstToPlay == 0)
            secondToPlay = 1;

        int count;

        //Nine rounds for each player to complete allocation
        for (count = 1; count <= 18; count++) {
            if (count % 2 == 1) {
                allocateUnitsActivePlayers(activePlayers[firstToPlay]);
                allocateUnitsPassivePlayers(risk.getPassivePlayers(), activePlayers[firstToPlay]);
            }
            if (count % 2 == 0) {
                allocateUnitsActivePlayers(activePlayers[secondToPlay]);
                allocateUnitsPassivePlayers(risk.getPassivePlayers(), activePlayers[secondToPlay]);
            }
        }
    }

    /* Method allocates the units for active players before the beginning of the main turn. (36 units for
     * each active player)
     *
     * @param risk, the game of risk currently being played.
     * @param activePlayers: the array of passivePlayers
     */
    public void allocateUnitsActivePlayers(ActivePlayer activePlayer) {
        int troops = 3;
        while (troops != 0) {
            window.getTextDisplay(activePlayer.getName() + ", please enter a country name belonging to you or a shortened version " +
                    "and the number of troops you want to allocate separated by a space, then press enter");
            window.getTextDisplay("You have " + troops + (troops == 1 ? " troop" : " troops") + " to allocate");
            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits

            // ensure that the players have entered both the country name and the number of units to be allocated
            input = e.validateCountryAndUnitsEntered(input);
            try {
                countryName = TextParser.parse(input[0].trim());
                activePlayer.getCountry(countryName);
                numberToAdd = Integer.parseInt(input[1]);

                // ensure that the number of units entered by the user is valid
                numberToAdd = e.validateNoUnitsAllocation(numberToAdd, troops);
                activePlayer.getCountry(countryName).setNumberOfUnits(activePlayer.getCountry(countryName).getNumberOfUnits() + numberToAdd);
                troops -= numberToAdd;
                window.updateMap();
                window.clearText();

            } catch (IllegalArgumentException | NullPointerException e) {
                window.sendErrorMessage("You entered the number of units to allocate or country name incorrectly");
            }
        }
    }


    /* Method allocates the units for passive players before the beginning of the main turn. (24 units for
     * each passive player)
     *
     * @param risk, the game of risk currently being played.
     * @param activePlayers: the array of passivePlayers
     */
    public void allocateUnitsPassivePlayers(PassivePlayer[] passivePlayers, ActivePlayer activePlayer) {
        //divide the units equally between the countries the passive players start with.
        window.getTextDisplay(activePlayer.getName() + ", now time to allocate the passive troops!\n");

        //Loop through each passive player and set the units with the given country name
        for (PassivePlayer p : passivePlayers) {
            window.clearText();
            window.getTextDisplay("To allocate troops for " + p.getName() +
                    " whose colour is " + getColorName(p) + " please enter their country name: ");
            String countryName = "";
            countryName = e.validateControlledBy(countryName, p);
            p.getCountry(countryName).setNumberOfUnits(p.getCountry(countryName).getNumberOfUnits() + 1);
            window.updateMap();
        }
        window.clearText();
    }


    /* private helper method, displays the what number the players have rolled
     *
     * @param activePlayers: an array containing information regarding the two main players
     * @param diceOne, diceTwo: integers representing the result of the die rolls
     */
    private void displayDiceRoll(ActivePlayer[] activePlayers, int diceOne, int diceTwo) {
        window.getTextDisplay(activePlayers[0].getName() + " rolled a " + diceOne + "\n"
                + activePlayers[1].getName() + " rolled a " + diceTwo);
    }

    /*helper method, rolls the dices for the active players to determine who will start the game. If it is a tie, the dices are rolled again.
     *
     * @param activePlayers: an array containing information regarding the two main players
     */
    public int whoStarts(ActivePlayer[] activePlayers) {
        int diceFirstPlayer = activePlayers[0].throwDice();
        int diceSecondPlayer = activePlayers[1].throwDice();

        //inform the user
        displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);

        while (diceFirstPlayer == diceSecondPlayer) // if it's a draw
        {
            window.getTextDisplay("It was a draw, let's roll again");
            diceFirstPlayer = activePlayers[0].throwDice();
            diceSecondPlayer = activePlayers[1].throwDice();
            displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);
        }

        if (diceFirstPlayer > diceSecondPlayer)
            return 0;
        else
            return 1;
    }

    /* private helper method, returns the index of the player whose turn is next.
     *
     * @return index of the next player
     */
    private int switchTurn()
    {
        if (currentPlayer == 0)
            return 1;
        else
            return 0;
    }

    /* private helper method, returns a string describing the colour assigned to the passive players
     *
     * @param playersPlayers: an array containing information regarding the four passive neutral players
     * @return the string description of the colour
     */
    private String getColorName(PassivePlayer passivePlayer) {
        if (passivePlayer.getPlayerColor().equals(new Color(177, 212, 174))) {
            return "Pale Green";
        } else if (passivePlayer.getPlayerColor().equals(new Color(235, 232, 234)))
            return "Light Grey";
        else if (passivePlayer.getPlayerColor().equals(new Color(248, 250, 162)))
            return "Pale Yellow";
        else if (passivePlayer.getPlayerColor().equals(new Color(237, 181, 126)))
            return "Peach";
        return null;
    }
}
