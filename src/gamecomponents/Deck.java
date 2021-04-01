package gamecomponents;

import ui.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Creates a new Deck object that stores an arrayList of Card objects
 */

public class Deck {
    private ArrayList<Card> cards;

    /**
     * creates a new Deck of 42 cards
     */
    public Deck() {
        /*
         * Card type needs to be initialised to a value in an array, game rules did not specify which countries had
         * which type of units
         */
        cards = new ArrayList<Card>();
        Random random = new Random();
        Random r = new Random();
        for (int i = 0; i < 42; i++) {
            int index = r.nextInt(3);
            cards.add(new Card(GameData.COUNTRY_NAMES[i], index));
        }
    }

    // collections method reorders the list randomly
    public void shuffle() {
        Collections.shuffle((List<Card>) cards);
    }

    //Accessor method for the instance variable cards
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public int size() {
    	return cards.size(); 
    }
}
