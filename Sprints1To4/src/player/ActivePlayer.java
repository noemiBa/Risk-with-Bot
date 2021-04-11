package player;

import gamecomponents.Card;
import gamecomponents.Deck;

import java.awt.*;
import java.util.Random;

/**
 * extends player class
 */

public class ActivePlayer extends Player {
    private int playerNumber;

    public ActivePlayer(int playerNumber, String name, Color playerColor) {
        super(name, playerColor);
        validatePlayerNumber(playerNumber);
        this.playerNumber = playerNumber;
    }

    public static ActivePlayer[] initialiseActivePlayers() {
        return new ActivePlayer[]
                {
                        new ActivePlayer(1, "", new Color(47, 206, 237)), //cyan
                        new ActivePlayer(2, "", new Color(199, 60, 194)), //pink
                };
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
    
    /* This method allows the player to draw a given number of cards and returns the Card that was drawn.
     * 
     * @param numberOfCards: the number of cards to be drawn; deck: the deck in use
     * @return: the card drawn 
     */
    public Card draw(int numberOfCards, Deck deck) {
    	Card drawn = null; 
    	
    	if (deck.size() == 0) { //if the deck is empty, create a new deck.
    		deck = new Deck(); 
    	}
    	
        for (int i = 1; i <= numberOfCards; i++) {
        	drawn = deck.getCards().get(0);
            getCards().add(drawn);
            deck.getCards().remove(0); 
        }
        return drawn;
    }
    
    /* Method allows the player to throw a dice - which will return a random number between 1 and 6.
     * 
     * @return the number 
     */
    public int throwDice() {
        Random r = new Random();
        return r.nextInt((6 - 1) + 1) + 1;
    }

    private void validatePlayerNumber(int playerNumber) {
        if (playerNumber <= 0) {
            throw new IllegalArgumentException("Player number should be positive");
        }
    }
}
	