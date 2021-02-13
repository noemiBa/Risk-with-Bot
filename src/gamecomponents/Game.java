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
    private Map map;
    private Player[] players;
    private Deck deck;

    public Game()
    {
        map = new Map();
        map.initialiseCountries();
        players = GameData.players;
        Player.assignCountriesControlled(players, map);
        deck = new Deck();
    }


    public Map getMap()
    {
        return map;
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public Deck getDeck()
    {
        return deck;
    }
}
