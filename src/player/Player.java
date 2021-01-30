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
}
