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
            testBot = new TestBot(880, 211); // set the position of the commandPanel here
        }
        catch (AWTException e)
        {
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

    /*
     * the tests below need to be altered
     */

    @Test
    void testAllocateUnitsActivePlayerInputRandomCharacters()
    {
        String[] testInput =
        {
                        "asdasdasdasdasdas",
                        risk.getActivePlayers()[0].getCountriesControlled().get(0).getName() + " 3"
        };
        allocateUnitsActivePlayers(testInput);
    }

    @Test
    void testAllocateUnitsActivePlayerInputRandomCharactersWithSpaceAndNumber()
    {
        String[] testInput =
        {
                "asdasdasdasdasdas 3",
                risk.getActivePlayers()[0].getCountriesControlled().get(1).getName() + " 3",
        };
        allocateUnitsActivePlayers(testInput);
    }

    @Test
    void testAllocateUnitsActivePlayerInputRandomCharactersWithOtherPlayersCountry()
    {
        String[] testInput =
        {
            risk.getActivePlayers()[1].getCountriesControlled().get(2).getName() + " 3",
            risk.getActivePlayers()[0].getCountriesControlled().get(3).getName() + " 3",
        };
        allocateUnitsActivePlayers(testInput);
    }

    @Test
    void testAllocateUnitsActivePlayerCorrectInput1()
    {
        String[] testInput =
        {
            risk.getActivePlayers()[0].getCountriesControlled().get(4).getName() + " 1",
            risk.getActivePlayers()[0].getCountriesControlled().get(5).getName() + " 1",
            risk.getActivePlayers()[0].getCountriesControlled().get(6).getName() + " 1"
        };
        allocateUnitsActivePlayers(testInput);
    }

//
    @Test
    void testAllocateUnitsActivePlayerCorrectInput2()
    {
        String[] testInput =
                {
                        risk.getActivePlayers()[0].getCountriesControlled().get(7).getName() + " 2",
                        risk.getActivePlayers()[0].getCountriesControlled().get(8).getName() + " 1"
                };
        allocateUnitsActivePlayers(testInput);
    }

    @Test
    void testAllocateUnitsActivePlayerCorrectInput3()
    {
        String[] testInput =
                {
                        risk.getActivePlayers()[0].getCountriesControlled().get(0).getName() + " 3"
                };
        allocateUnitsActivePlayers(testInput);
    }

    @Test
    void testIncorrectNumberOfUnits()
    {
        String[] testInput =
        {
            risk.getActivePlayers()[0].getCountriesControlled().get(1).getName() + " 0",
            risk.getActivePlayers()[0].getCountriesControlled().get(2).getName() + " 4",
            risk.getActivePlayers()[0].getCountriesControlled().get(3).getName() + " 3"
        };
        allocateUnitsActivePlayers(testInput);
    }


    public void allocateUnitsActivePlayers(String[] testInput)
    {
        Country.initialiseUnits(risk.getMap().getCountries());
        risk.getTurns().setGameStage(Turns.stage.ASSIGN_COUNTRIES);
        risk.getWindow().updatePlayerInfo(risk.getTurns().getGameStage(), risk.getActivePlayers(), risk.getPassivePlayers());
        int troops = 3;
        int i = 0;
        while (troops != 0)
        {
            risk.getWindow().getTextDisplay(risk.getActivePlayers()[0].getName() +  ", please enter a country name belonging to you or a shortened version " +
                    "and the number of troops you want to allocate separated by space, then press enter");
            risk.getWindow().getTextDisplay("You have " + troops + (troops == 1? " troop": " troops") + " to allocate");
            String countryName = "";
            int numberToAdd = -1;
            testBot.enterText(testInput[i++]);

            String[] input = risk.getWindow().getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            if(input.length != 2)
            {
                risk.getWindow().clearText();
                risk.getWindow().sendErrorMessage("You must enter a country and a number");
            }
            else
            {
                try
                {
                    risk.getActivePlayers()[0].getCountriesControlled().get(input[0]);
                    countryName = TextParser.parse(input[0]);
                    numberToAdd = Integer.parseInt(input[1]);
                    if(numberToAdd > 3 || numberToAdd < 1 || troops < numberToAdd)
                    {
                        risk.getWindow().clearText();
                        risk.getWindow().sendErrorMessage("You can enter at most " + troops + " for this allocation");
                    }
                    else
                    {
                        risk.getActivePlayers()[0].getCountriesControlled().get(countryName).setNumberOfUnits(risk.getActivePlayers()[0]
                        .getCountriesControlled().get(countryName).getNumberOfUnits() + numberToAdd);
                        troops -= numberToAdd;
                        risk.getWindow().updateMap();
                        risk.getWindow().clearText();
                    }
                }
                catch(NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e)
                {
                    risk.getWindow().clearText();
                    risk.getWindow().sendErrorMessage("You entered the number or country name incorrectly");
                }
            }
        }
    }
}
