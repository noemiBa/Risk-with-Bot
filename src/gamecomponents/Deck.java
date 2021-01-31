package gamecomponents;

import player.ActivePlayer;
import ui.Map;
import java.util.ArrayList;
import java.util.Random;

/** Creates a new Deck object that stores an arrayList of Card objects
 * */

public class Deck
{
    private ArrayList <Card> cards;

    /**
     * creates a new Deck of 42 cards, currently unsure what Card.type should be, if anyone knows this
     * it would be a good idea to create an array of units each with a different enum which can be used to assign
     * each card a different unit unit type
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

    /**
     * the two indexes are always unique.
     */
    public void shuffle()
    {
        int cardIndexFirst = 0;
        int cardIndexSecond = 0;
        Random random = new Random();
        Card cardTemp = new Card("", Card.type.INFANTRY); // used to temporarily store a Card object for swapping cards
        for(int i = 0; i < 21; i++)
        {
           cardIndexFirst = random.nextInt(42);
           int cardIndexTemp = random.nextInt(42);
           while(cardIndexFirst == cardIndexTemp)
           {
               cardIndexTemp = random.nextInt(42); //generate and discard a random integer
           }
           cardIndexSecond = cardIndexTemp; 
           
           /**
             * Swaps the instance variables of two cards objects
             * */
            cardTemp.setCountryName(cards.get(cardIndexFirst).getCountryName());
            cardTemp.setUnitType(cards.get(cardIndexFirst).getUnitType());
            cards.get(cardIndexFirst).setCountryName(cards.get(cardIndexSecond).getCountryName());
            cards.get(cardIndexFirst).setUnitType(cards.get(cardIndexSecond).getUnitType());
            cards.get(cardIndexSecond).setCountryName(cardTemp.getCountryName());
            cards.get(cardIndexSecond).setUnitType(cardTemp.getUnitType());
        }
    }

    public ArrayList <Card> getCards()
    {
        return cards;
    }
}
