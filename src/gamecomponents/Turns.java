package gamecomponents;

import com.botharmon.Game;
import lib.CustomArrayList;
import lib.TextParser;
import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;
import ui.Map;
import ui.Window;

import java.awt.*;
import java.util.ArrayList;

public class Turns {
    private Window window;

    // Can be modified, feel free to change the ENUM values to add, remove, reorder stages of the game
    public enum stage {
        ENTER_NAMES,
        ASSIGN_COUNTRIES,
        ROLL_TO_PLACE_TERRITORIES,
        MAIN_TURN,
        END
    }

    private stage gameStage = stage.ENTER_NAMES;

    public stage getGameStage() {
        return gameStage;
    }

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
                int playerWhoStartsAllocating = whoStarts(risk.getActivePlayers());
                allocateUnits(risk.getActivePlayers(), playerWhoStartsAllocating, risk);
                //allocateUnitsPassivePlayers(risk.getPassivePlayers());
                setGameStage(stage.MAIN_TURN);
                break;
            case MAIN_TURN:
                int playerWhoStartsMainTurn = whoStarts(risk.getActivePlayers());
                while (endMainTurn(risk.getActivePlayers()) != true) {
                    mainTurn(risk.getActivePlayers(), playerWhoStartsMainTurn);
                }
                setGameStage(stage.END);
                break;
            case END:
                window.getTextDisplay("Congratulations " + getWinnerName(risk.getActivePlayers()) + " you conqueered the world! Thanks for playing");
            default:
                break;
        }
    }

    public void enterPlayerNames(Game risk) {
        //welcome the user and ask them to enter their names
        window.getTextDisplay("Welcome, player " + risk.getActivePlayers()[0].getPlayerNumber() + "! Enter your name: ");
        String playerName = null;
        for (int i = 0; i < risk.getActivePlayers().length; ) {
            //take what the user types and put it in the string playerName
            playerName = risk.getWindow().getCommand();
            if (playerName.length() <= 20) {
                risk.getActivePlayers()[i].setName(validatePlayerName(playerName));
                i++;
                if (i < risk.getActivePlayers().length)
                    window.getTextDisplay("Welcome, player " + risk.getActivePlayers()[i].getPlayerNumber() + "! Enter your name: ");
            } else
                window.getTextDisplay("Sorry, that name is too long. Try typing a shortened version. It must be no longer than 20 characters.");
        }

        validateDifferentName(risk.getActivePlayers(), playerName, risk);

        window.clearText();
    }

    public void assignCountries(Game risk) {
        Country.initialiseUnits(risk.getMap().getCountries());
        window.getTextDisplay(risk.getActivePlayers()[0].getName() + ", you drew the Countries: " + risk.getActivePlayers()[0].printCountries());
        window.getTextDisplay(risk.getActivePlayers()[1].getName() + ", you drew the Countries: " + risk.getActivePlayers()[1].printCountries());
        risk.getDeck().shuffle();
    }

    public void allocateUnits(ActivePlayer[] activePlayers, int firstToPlay, Game risk) {
        //print instructions
        int secondToPlay = 0;

        //changing value case is equal
        if (firstToPlay == 0)
            secondToPlay = 1;

        int count;

        //Nine rounds for each player to complete allocation
        for (count = 1; count <= 2; count++) {
            if (count % 2 == 1) {
                allocateUnitsActivePlayers(activePlayers[firstToPlay]);
                allocateUnitsPassivePlayers(risk.getPassivePlayers(), activePlayers[firstToPlay]);
            }
            if (count % 2 == 0) {
                allocateUnitsActivePlayers(activePlayers[secondToPlay]);
                allocateUnitsPassivePlayers(risk.getPassivePlayers(), activePlayers[secondToPlay]);
            }
            System.out.println("round " + count);
        }
    }

    public void allocateUnitsActivePlayers(ActivePlayer activePlayer) {
        int troops = 3;
        while (troops != 0) {
            window.getTextDisplay(activePlayer.getName() + ", please enter a country name belonging to you or a shortened version " +
                    "and the number of troops you want to allocate separated by space, then press enter");
            window.getTextDisplay("You have " + troops + (troops == 1 ? " troop" : " troops") + " to allocate");
            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            if (input.length != 2) {
                window.clearText();
                window.sendErrorMessage("You must enter a country and a number");
            } else {
                try {
                    activePlayer.getCountriesControlled().get(input[0]);
                    countryName = TextParser.parse(input[0]);
                    numberToAdd = Integer.parseInt(input[1]);
                    if (numberToAdd > 3 || numberToAdd < 1 || troops < numberToAdd) {
                        window.clearText();
                        window.sendErrorMessage("You can enter at most " + troops + " for this allocation");
                    } else {
                        activePlayer.getCountriesControlled().get(countryName).setNumberOfUnits(activePlayer.getCountriesControlled()
                                .get(countryName).getNumberOfUnits() + numberToAdd);
                        troops -= numberToAdd;
                        window.updateMap();
                        window.clearText();
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    window.clearText();
                    window.sendErrorMessage("You entered the number or country name incorrectly");
                }
            }
        }
    }

    public void allocateUnitsPassivePlayers(PassivePlayer[] passivePlayers, ActivePlayer activePlayer) {
        //divide the units equally between the countries the passive players start with.
        window.getTextDisplay(activePlayer.getName() + ", now time to allocate the passive troops!\n");

        //Loop through each passive player and set the units with the given country name
        for (PassivePlayer p : passivePlayers) {
            window.getTextDisplay("To allocate troops for " + p.getName() +
                    " who's colour is " + getColorName(p) + " please enter their country name: ");

            String input = window.getCommand(); //splits the string between letters and digits
            String countryName = TextParser.parse(input);
            countryName = validateCountry(countryName, p);
            p.getCountriesControlled().get(countryName).setNumberOfUnits(p.getCountriesControlled().get(countryName).getNumberOfUnits() + 1);
            window.updateMap();
        }
        window.clearText();
    }

    public void mainTurn(ActivePlayer[] activePlayers, int firstToPlay) {
        window.getTextDisplay("Lets Start the game! This turn consist in Reinforcements, Attack, Draw a card and Reallocate");
        int secondToPlay = 0;
        if (firstToPlay == 0) {
            secondToPlay = 1;
        }
        reinforcementsAllocation(activePlayers[firstToPlay]);

        reinforcementsAllocation(activePlayers[secondToPlay]);
        window.clearText();
    }

    public void reinforcementsAllocation(ActivePlayer activePlayer) {
        int numberOfReinforcements = numberOfReinforcements(activePlayer);

        window.getTextDisplay(activePlayer.getName() + " it is your turn to reinforce!");
        while (numberOfReinforcements != 0) {
            window.getTextDisplay("\bYou have " + numberOfReinforcements + (numberOfReinforcements == 1 ? " reinforcement." : " reinforcements.\b")
                    + "\nPlease enter a country name belonging to you or a shortened version and the number of troops you want to allocate separated by space, then press enter");

            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            if (input.length != 2) {
                window.clearText();
                window.sendErrorMessage("You must enter a country and a number");
            } else {
                try {
                    activePlayer.getCountriesControlled().get(input[0]);
                    countryName = TextParser.parse(input[0]);
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
                } catch (NumberFormatException | NullPointerException e) {
                    window.clearText();
                    window.sendErrorMessage("You entered the number or country name incorrectly");
                }
                System.out.println(numberOfReinforcements);
            }
        }
    }

    public int numberOfReinforcements(ActivePlayer activePlayer) {
        //3 is the minimum by the rules
        if (Player.numberOfCountriesControlledBy(activePlayer) < 6) {
            return 3;
        } else {
            return Player.numberOfCountriesControlledBy(activePlayer) / 2;
        }
    }

    /* private helper method, displays the what number the players have rolled
     *
     * @param activePlayers: an array containing information regarding the two main players
     * @param diceOne, diceTwo: int representing the result of the die rolls
     */
    public void displayDiceRoll(ActivePlayer[] activePlayers, int diceOne, int diceTwo) {
        window.getTextDisplay(activePlayers[0].getName() + " rolled a " + diceOne + "\n"
                + activePlayers[1].getName() + " rolled a " + diceTwo);
    }

    public int whoStarts(ActivePlayer[] activePlayers) {
        int diceFirstPlayer = activePlayers[0].throwDice();
        int diceSecondPlayer = activePlayers[1].throwDice();
        //inform the user
        displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);

        while (diceFirstPlayer == diceSecondPlayer) // if it's a draw
        {
            window.getTextDisplay("It was a draw, let's roll again");
            displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);

            diceFirstPlayer = activePlayers[0].throwDice();
            diceSecondPlayer = activePlayers[1].throwDice();
        }

        if (diceFirstPlayer > diceSecondPlayer)
            return 0;
        else
            return 1;
    }

    /* private helper method, displays an error message if the player has entered an invalid input as their name.
     *
     * @param risk: the game currently being played
     * @param playerName: the input entered by the user
     * @return the valid playerName
     */
    public String validatePlayerName(String playerName) {
        //while the player name is an empty String or a number
        while (playerName.trim().isEmpty() || playerName.matches("[0-9]+")) {
            window.sendErrorMessage("That doesn't quite look like a name, try again!");
            playerName = window.getCommand(); //send an error message and take and discard the input
        }
        return playerName;
    }

    /* public helper method, ask to second player to enter a different name than player one
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

    /* private helper method, displays an error message if the player has entered an invalid input as the number of Units to Add.
     *
     * @param numberToAdd: the input entered by the user
     * @return the valid numberToAdd
     */
    public int validateNoUnits(int numberToAdd, int numberUnitsLeft, String countryName) {

        if (numberToAdd == -1) { //numberToAdd is initialised as -1. If by the time this method is called numberToAdd is still -1, there was an issue with the user inp
            window.sendErrorMessage("You forgot to type the units you want to allocate to " + countryName + "\nEnter the number: ");
            return -1;
        } else if (numberToAdd > 3 || numberToAdd < 1) {
            window.sendErrorMessage("That is invalid, please enter a number between 1-3 to add to " + countryName);
            return -1;
        } else if (numberToAdd > numberUnitsLeft) {
            window.sendErrorMessage("You don't have that many units left! You only have " + numberUnitsLeft + " enter a new valid number to add to " + countryName);
            return -1;
        }
        window.clearText();
        return numberToAdd;
    }

    /* private helper method, displays an error message if the player has entered an invalid input (or ambiguous name) as country to add units to.
     *
     * @param countryName: the input entered by the user
     * @return the valid countryName
     */
    public String validateCountry(String countryName, Player player) {

        CustomArrayList<Country> countries = player.getCountriesControlled();

        while (countries.get(countryName) == null) {
            window.sendErrorMessage("Sorry, it looks like " + player.getName() + " does not own this country or you typed it wrong!"
                    + "\nEnter a country of " + player.getName() + "'s colour");
            countryName = TextParser.parse(window.getCommand().trim());
        }

        window.clearText();
        return countryName;
    }

    public String getColorName(PassivePlayer passivePlayer) {
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

    public boolean endMainTurn(ActivePlayer[] activePlayers) {
        for (ActivePlayer a : activePlayers) {
            if (a.numberOfCountriesControlledBy(a) == 42) {
                return true;
            }
        }
        return false;
    }

    public String getWinnerName(ActivePlayer[] activePlayers) {
        for (ActivePlayer a : activePlayers) {
            if (a.numberOfCountriesControlledBy(a) == 42) {
                return a.getName();
            }
        }
        return null;
    }
}
