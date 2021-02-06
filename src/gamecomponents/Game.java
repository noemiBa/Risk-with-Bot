package gamecomponents;

import player.*;
import ui.Map;
import ui.Window;

import java.awt.*;

/**
 * Game class created to run different the main class methods in each of the packages
 * Currently used to test creating an Array of player objects and .draw() method
 */

public class Game
{
    private static Player[] players;
    private Map map;
    private Deck deck;
	
    public Game()
    {
        map = new Map();
        map.initialiseCountries();
        players = newPlayers();
        Player.assignCountriesControlled(players, map);
        deck = new Deck();
    }
    
    public static Player[] newPlayers()
    {
        Player[] players =
        {
            new ActivePlayer("", Color.cyan),
            new ActivePlayer("", Color.magenta),
            new PassivePlayer("Benny", Color.green),
            new PassivePlayer("Harry", Color.white),
            new PassivePlayer("Jolene", Color.ORANGE),
            new PassivePlayer("Borgov", Color.yellow)
        };
        return players;
    }

    public static Player[] getPlayers()
    {
        return players;
    }

    public Map getMap()
    {
        return map;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public static void main()
    {
        Game game = new Game();
        Window window = new Window(game);
        game.getDeck().shuffle();
    }
}
