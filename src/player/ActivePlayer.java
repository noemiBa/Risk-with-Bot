package player;

import gamecomponents.Deck;

import java.awt.*;
import java.util.Random;

/** extends player class
 * */

public class ActivePlayer extends Player
{
    private int playerNumber;

    public ActivePlayer(int playerNumber, String name, Color playerColor) {	
        super(name, playerColor);
        validatePlayerNumber(playerNumber);
        this.playerNumber = playerNumber;
    }

    public static ActivePlayer[] initialiseActivePlayers()
    {
        return new ActivePlayer[]
        {
            new ActivePlayer(1,"", new Color(47, 206, 237)), //cyan
            new ActivePlayer(2,"", new Color(199, 60, 194)), //pink
        };
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public void draw(int numberOfCards, Deck deck)
    {
        if(getCards().size() <= 5)
        {
            for(int i = 1; i <= numberOfCards; i++)
            {
                getCards().add(deck.getCards().get(0));
                deck.getCards().remove(0);
            }
        }
    }

    public int throwDice()
    {
        Random r = new Random();
        return r.nextInt((6 - 1) + 1) + 1;
    }
    
    private void validatePlayerNumber(int playerNumber) {
    	if (playerNumber <= 0) {
    		throw new IllegalArgumentException("Player number should be positive");
    	}
    }
}
	