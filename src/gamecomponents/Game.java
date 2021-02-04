package gamecomponents;

import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;
import ui.Window;

import java.util.ArrayList;

/**
 * Game class created to run different the main class methods in each of the packages
 * Currently used to test creating an Array of player objects and .draw() method
 */

public class Game {
    private static Player[] players;
    public static String namePlayer1;
    public static String namePlayer2;

    public Game() {

    }

    ;

    public static void main() {
        Game game = new Game();
        Window window = new Window();


        Player[] players = game.getPlayers();

        for (Player p : players) {
            System.out.println(p.getName() + " in Game");
        }
    }


    public String getNamePlayer1() {
        return namePlayer1;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public Player[] getPlayers() {
        return startPlayers();
    }


    public Player[] startPlayers() {
        Player[] players = {
                new ActivePlayer(getNamePlayer1(), ActivePlayer.color.BLUE),
                new ActivePlayer(getNamePlayer2(), ActivePlayer.color.GREEN),
                new PassivePlayer("Benny", PassivePlayer.color.BROWN),
                new PassivePlayer("Harry", PassivePlayer.color.GREY),
                new PassivePlayer("Jolene", PassivePlayer.color.ORANGE),
                new PassivePlayer("Borgov", PassivePlayer.color.YELLOW)};
        for (Player p : players) {
            System.out.println(p.getName() + " in Window");
        }
        Deck deck = new Deck();
        deck.shuffle();
        Player.assignCountries(players, deck);

        for (Player p : players) {
            System.out.println(p.getName() + " has the countries: " + p.getCards());
        }
        System.out.println("players instantiated");
        return players;
    }
}
