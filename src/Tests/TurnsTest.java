package Tests;

import static org.junit.jupiter.api.Assertions.*;

import com.botharmon.Game;
import gamecomponents.Country;
import gamecomponents.Deck;
import gamecomponents.Turns;
import lib.TextParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import player.ActivePlayer;
import player.PassivePlayer;

import player.Player;
import ui.Map;
import ui.Window;
import java.awt.*;

public class TurnsTest
{
    private static Game risk;
    private static TestBot testBot;

    static
    {
        try
        {
            testBot = new TestBot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public TurnsTest() throws AWTException {}

    @BeforeAll
    public static void init()
    {
        risk = new Game();
        risk.setMap(new Map());
        risk.setActivePlayers(ActivePlayer.initialiseActivePlayers());
        risk.setPassivePlayers(PassivePlayer.initialisePassivePlayers());
        Player.assignCountriesControlled(risk.getActivePlayers(), risk.getPassivePlayers(), risk.getMap());
        risk.setDeck(new Deck());
        risk.setWindow(new Window(risk));
        risk.setTurns(new Turns());
        risk.getTurns().setWindow(risk.getWindow());
    }

    @Test
    void testEnterPlayerNames()
    {
        String[] testInput = {"noemiiiiiiiiiiiiiiiii", "jess", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "beca"};
        risk.getWindow().getTextDisplay("Welcome, player " + risk.getActivePlayers()[0].getPlayerNumber() + "! Enter your name: ");
        String playerName;
        for (int i = 0, j = 0; i < risk.getActivePlayers().length; j++)
        {
            //take what the user types and put it in the string playerName
            testBot.enterText(testInput[j]);
            playerName = risk.getWindow().getCommand();
            if(playerName.length() <= 20)
            {
                risk.getActivePlayers()[i].setName(risk.getTurns().validatePlayerName(playerName));
                i++;
                if(i < risk.getActivePlayers().length)
                    risk.getWindow().getTextDisplay("Welcome, player " + risk.getActivePlayers()[i].getPlayerNumber() + "! Enter your name: ");
            }
            else
                risk.getWindow().getTextDisplay("Sorry, that name is too long. Try typing a shortened version. It must be no longer than 20 characters.");
        }
        assertEquals("jess", risk.getActivePlayers()[0].getName());
        assertEquals("beca", risk.getActivePlayers()[1].getName());
    }

    @Test
    void testAssignCountries()
    {
        risk.getTurns().assignCountries(risk);
        for(Country c: risk.getMap().getCountries())
        {
            assertNotNull(c.getControlledBy());
        }
    }

    @Test
    void whoStarts()
    {
        for(int i = 0; i < 10; i++)
        {
            int playerIndexWhoStarts = risk.getTurns().whoStarts(risk.getActivePlayers());
            assertTrue(playerIndexWhoStarts == 1 || playerIndexWhoStarts == 0);
        }
    }

//    @Test
//    void testAllocateUnitsActivePlayersOtherActivePlayerInput()
//    {
//        allocateUnitsActivePlayers(risk.getActivePlayers()[1].getCountriesControlled().get(1).getName() + " " + 3);
//    }
//
//    @Test
//    void testAllocateUnitsActivePlayerInputRandomCharacters()
//    {
//
//        allocateUnitsActivePlayers("asdasdasdasdasdas");
//    }

//    @Test
//    void testAllocateUnitsActivePlayerInputRandomCharactersWithSpaceAndNumber()
//    {
//        allocateUnitsActivePlayers("asdasdasdasdasdas 3");
//    }

//    @Test
//    void testAllocateUnitsActivePlayerCorrectInput1()
//    {
//        allocateUnitsActivePlayers(risk.getActivePlayers()[0].getCountriesControlled().get(0) + " 1");
//    }

//    @Test
//    void testAllocateUnitsActivePlayerCorrectInput2()
//    {
//        allocateUnitsActivePlayers(risk.getActivePlayers()[0].getCountriesControlled().get(0) + " 2");
//    }

//    @Test
//    void testAllocateUnitsActivePlayerCorrectInput3()
//    {
//        allocateUnitsActivePlayers(risk.getActivePlayers()[0].getCountriesControlled().get(0) + " 3");
//    }

    public void allocateUnitsActivePlayers(String testInput)
    {
        Country.initialiseUnits(risk.getMap().getCountries());
        risk.getTurns().setGameStage(Turns.stage.ASSIGN_COUNTRIES);
        risk.getWindow().updatePlayerInfo(risk.getTurns().getGameStage(), risk.getActivePlayers(), risk.getPassivePlayers());
        int troops = 3;
        while(troops != 0)
        {
            risk.getWindow().getTextDisplay(risk.getActivePlayers()[0].getName() + ", you have " + troops + " in total to allocate. Please enter the country name or a short version and the number of troops you want to allocate separate by space, then press enter");

            String countryName = "";
            int numberToAdd = -1;
            try
            {
                testBot.enterText(testInput);
                String[] input = risk.getWindow().getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
                input[0] = input[0].trim();
                countryName = TextParser.parse(input[0]);
                numberToAdd = Integer.parseInt(input[1]); //NEED TO VALIDATE that it is actually an integer
            }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {} //if we enter this catch statement, it means the user only entered the country name and no no. of units / or entered a string instead of a number.

            //validate the user's input
            numberToAdd = risk.getTurns().validateNoUnits(numberToAdd, troops, countryName);
            countryName =  risk.getTurns().validateCountryName(countryName);

            risk.getActivePlayers()[0].getCountriesControlled().get(countryName).setNumberOfUnits
                    (risk.getActivePlayers()[0].getCountriesControlled().get(countryName).getNumberOfUnits() + numberToAdd);

            troops = troops - numberToAdd;

            System.out.println("Allocate " + numberToAdd + " to " + countryName); //For test purpose
            risk.getWindow().updateMap(); // updateUI can be moved to where appropriate part of the method, temporarily put here
            risk.getWindow().clearText();
        }
    }
}