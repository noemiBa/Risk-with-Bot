package gamecomponents;

import player.*;
import ui.Window;

import java.util.ArrayList;

/**
 * Game class created to run different the main class methods in each of the packages
 * Currently used to test creating an Array of player objects and .draw() method
 */

public class Game
{

    //public Game() {}
    private static Player[] players;

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

    public static Player[] getPlayers()
    {
        return players;
    }

    public static void main()
    {
        Game game = new Game();
        players = newPlayers();
        Window window = new Window();
        Deck deck = new Deck();

        deck.shuffle();
        Player.assignCountries(players, deck);
    }
}
