package player;

import gamecomponents.Card;
import gamecomponents.Deck;

import java.util.ArrayList;

/** implements Player interface
 * */
public class PassivePlayer implements Player
{
    public enum color {BROWN, GREEN, GREY, BLUE, YELLOW, ORANGE};

    private String name;
    private color playerColor;
    private ArrayList<Card> cards;

    public PassivePlayer(String name, color playerColor)
    {
        this.name = name;
        this.playerColor = playerColor;
        cards = new ArrayList <Card>();
    }

    public String getName()
    {
        return name;
    }

    public color getPlayerColor()
    {
        return playerColor;
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    /** Removes the number of cards specified from the top of the deck ArrayList and
     * adds to Player cards ArrayList
     * */
    public void draw(int numberOfCards, Deck deck)
    {
        for(int i = 1; i <= numberOfCards; i++)
        {
            cards.add(deck.getCards().get(0));
            deck.getCards().remove(0);
        }
    }
}
