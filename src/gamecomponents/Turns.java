package gamecomponents;

import com.botharmon.Game;
import lib.CustomArrayList;
import lib.ErrorHandler;
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
    private static int currentPlayer = 0;
    private ErrorHandler e; 
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
        int currentPlayer = 0;
        while (gameStage != stage.END) {
            makeTurns(risk);
            // for testing purposes
            System.out.println(gameStage);
        }
    }

    // each players turn for each stage is implemented here
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
                mainTurn(risk.getActivePlayers()[currentPlayer]);
                currentPlayer = switchTurn();
                break;
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
                risk.getActivePlayers()[i].setName(e.validatePlayerName(playerName));
                i++;
                if (i < risk.getActivePlayers().length)
                    window.getTextDisplay("Welcome, player " + risk.getActivePlayers()[i].getPlayerNumber() + "! Enter your name: ");
            } else
                window.getTextDisplay("Sorry, that name is too long. Try typing a shortened version. It must be no longer than 20 characters.");
        }

        e.validateDifferentName(risk.getActivePlayers(), playerName, risk);

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
            countryName = e.validateCountry(countryName, p);
            p.getCountriesControlled().get(countryName).setNumberOfUnits(p.getCountriesControlled().get(countryName).getNumberOfUnits() + 1);
            window.updateMap();
        }
        window.clearText();
    }

    // methods for each turn are called here
    public void mainTurn(ActivePlayer activePlayer)
    {
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

    private int switchTurn()
    {
        if (currentPlayer == 0)
            return 1;
        else
            return 0;
    }

    public int numberOfReinforcements(ActivePlayer activePlayer) {
        //3 is the minimum by the rules
        if (activePlayer.getCountriesControlled().size() < 6) {
            return 3;
        } else {
            return activePlayer.getCountriesControlled().size() / 2;
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
            diceFirstPlayer = activePlayers[0].throwDice();
            diceSecondPlayer = activePlayers[1].throwDice();
            displayDiceRoll(activePlayers, diceFirstPlayer, diceSecondPlayer);
        }

        if (diceFirstPlayer > diceSecondPlayer)
            return 0;
        else
            return 1;
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

    // checkWinner can be called after each active player attack, this is the only time, the number of countries each player controls will change
    public void checkWinner(ActivePlayer[] activePlayers) {
        for (ActivePlayer a : activePlayers) {
            if (a.getCountriesControlled().size() == 42) {
                window.getTextDisplay("Congratulations " + a.getName() + " you conquered the world! Thanks for playing");
                setGameStage(stage.END);
            }
        }
    }
}
