	package player;

import gamecomponents.Card;
import gamecomponents.Deck;

import java.util.ArrayList;

/** Player interface implemented by ActivePlayer and PassivePlayer
 * */
public class Player
{

	public enum color {BROWN, GREEN, GREY, BLUE, YELLOW, ORANGE};
    private String name;
    private PassivePlayer.color playerColor;
    private ArrayList<Card> cards;

    public Player(String name, color playerColor)
    {
        this.name = name;
        this.playerColor = playerColor;
        cards = new ArrayList <Card>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public PassivePlayer.color getPlayerColor()
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
    
    @Override
	public String toString() {
		return "[name=" + name + ", playerColor=" + playerColor + "]";
	}
}
