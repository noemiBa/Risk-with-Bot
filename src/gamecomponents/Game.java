package gamecomponents;

import player.*;
import ui.Map;
import ui.Window;

/**
 * Game class created to run different the main class methods in each of the packages
 * Currently used to test creating an Array of player objects and .draw() method
 */

public class Game
{
	private Map map;
    private Player[] players;
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
            new ActivePlayer("", ActivePlayer.color.BLUE),
            new ActivePlayer("", ActivePlayer.color.GREEN),
            new PassivePlayer("Benny", PassivePlayer.color.BROWN),
            new PassivePlayer("Harry", PassivePlayer.color.GREY),
            new PassivePlayer("Jolene", PassivePlayer.color.ORANGE),
            new PassivePlayer("Borgov", PassivePlayer.color.YELLOW)
        };
        return players;
    }

    public Player[] getPlayers()
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
