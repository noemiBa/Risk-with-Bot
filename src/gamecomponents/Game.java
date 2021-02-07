package gamecomponents;

import player.*;
import ui.Map;
import ui.Window;

import java.awt.*;

/**
 * Game class - creates the main components of the game i.e. the game's components, the ui, shuffling the deck
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
            new ActivePlayer("", new Color(47, 206, 237)), //cyan
            new ActivePlayer("", new Color(199, 60, 194)), //pink
            new PassivePlayer("Benny", new Color(177, 212, 174)), //pale green
            new PassivePlayer("Harry", new Color(235, 232, 234)), //light grey
            new PassivePlayer("Jolene", new Color(248, 250, 162)), //pale yellow
            new PassivePlayer("Borgov", new Color(237, 181, 126)) //peach
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
