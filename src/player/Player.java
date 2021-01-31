package player;

import gamecomponents.Card;
import gamecomponents.Deck;

import java.util.ArrayList;

/** Player interface implemented by ActivePlayer and PassivePlayer
 * */
public interface Player
{
    public String getName();
    public PassivePlayer.color getPlayerColor();
    public ArrayList<Card> getCards();
    public void draw(int numberOfCards, Deck deck);

    public static void assignCountries(Player[] players, Deck deck)
    {
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
    }
}
