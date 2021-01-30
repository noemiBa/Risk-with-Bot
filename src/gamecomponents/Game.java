package gamecomponents;

import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;
import ui.Window;

/** Game class created to run different the main class methods in each of the packages
 * Currently used to test creating an Array of player objects and .draw() method
 * */

public class Game
{
    public Game() {};
    public static void main()
    {
        Player[] players = {
                new ActivePlayer("test1", ActivePlayer.color.GREEN),
                new ActivePlayer("test2", ActivePlayer.color.GREEN),
                new PassivePlayer("test3", PassivePlayer.color.GREEN),
                new PassivePlayer("test4", PassivePlayer.color.GREEN),
                new PassivePlayer("test5", PassivePlayer.color.GREEN),
                new PassivePlayer("test6", PassivePlayer.color.GREEN)

        };
        Deck deck = new Deck();
        deck.shuffle();
        for(Player player: players)
        {
            if(player instanceof ActivePlayer)
                player.draw(8, deck);
            else
                player.draw(6, deck);

            /**
             * method could be added here to allocate countries controlled using the player cards
             * countryName variable
             */
        }
        Window.main();
    }
}
