package gamecomponents;

import player.ActivePlayer;
import ui.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/** Creates a new Deck object that stores an arrayList of Card objects
 * */

public class Deck
{
    private ArrayList <Card> cards;

    /**
     * creates a new Deck of 42 cards
     */
    public Deck()
    {
        /*
         * Card type needs to be initialised to a value in an array, game rules did not specify which countries had
         * which type of units
         */
        cards = new ArrayList<Card>();
        for(int i = 0; i < 42; i++)
            cards.add(new Card(Map.COUNTRY_NAMES[i], Card.type.INFANTRY));
    }

    // collections method reorders the list randomly
    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public ArrayList <Card> getCards()
    {
        return cards;
    }
}
