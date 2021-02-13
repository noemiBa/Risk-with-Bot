package player;

import gamecomponents.Card;
import gamecomponents.Country;
import gamecomponents.Deck;
import gamecomponents.GameData;
import ui.Map;

import java.awt.*;
import java.util.ArrayList;
import lib.CustomArrayList;
import java.util.Collections;

/** Player abstract class extended by ActivePlayer and PassivePlayer
 * */
public abstract class Player
{
    private String name;
    private Color playerColor;
    private ArrayList <Card> cards;
    private CustomArrayList<Country> countriesControlled;

    public Player(String name, Color playerColor)
    {
        this.name = name;
        this.playerColor = playerColor;
        cards = new ArrayList <Card>();
        countriesControlled = new CustomArrayList<Country>();
    }

    public Player(){}
    
    /*Accessor methods*/
    public CustomArrayList <Country> getCountriesControlled()
    {
        return countriesControlled;
    }

    public String getName()
    {
        return name;
    }
    
    public Color getPlayerColor()
    {
        return playerColor;
    }
    
    public ArrayList<Card> getCards()
    {
        return cards;
    }
    
    /*Mutator methods*/
    public void setName(String name)
    {
        this.name = name;
    }

    /** Removes the number of cards specified from the top of the deck ArrayList and
     * adds to Player cards ArrayList
     * */
    private void draw(int numberOfCards, Deck deck)
    {
        for(int i = 1; i <= numberOfCards; i++)
        {
            cards.add(deck.getCards().get(0));
            deck.getCards().remove(0);
        }
    }

    public static void assignCountriesControlled(Player[] players, Map map)
    {
        ArrayList<Integer> randomIndexes = new ArrayList<Integer>();
        for(int i = 0; i < 42; i++)
        {
            randomIndexes.add(i);
        }

        // randomizes order of 42 integers labelled 0 - 41
        Collections.shuffle(randomIndexes);

        // assigns random countries to each player using randomIndexes to reference the country ArrayList
        int j = 0;
        for(Player p: players)
        {
            if(p instanceof ActivePlayer)
            {
                for(int i = 0; i < GameData.INIT_COUNTRIES_PLAYER; i++, j++)
                {
                    int currentJ = randomIndexes.get(j);
                    p.getCountriesControlled().add(map.getCountries().get(currentJ).getName(), map.getCountries().get(currentJ));
                }
            }
            else
            {
                for(int i = 0; i < GameData.INIT_COUNTRIES_NEUTRAL; i++, j++)
                {
                    int currentJ = randomIndexes.get(j);
                    p.getCountriesControlled().add(map.getCountries().get(currentJ).getName(), map.getCountries().get(currentJ));
                }
            }
        }
    }

    public static void initialiseUnits(Player[] players)
    {
        for(Player p: players)
        {
            for (Country c: p.getCountriesControlled())
            {
                c.setNumberOfInfantry(1);
            }
        }
    }
    
    @Override
	public String toString()
	{
		return "[name=" + name + ", playerColor=" + playerColor + "]";
	}
}
