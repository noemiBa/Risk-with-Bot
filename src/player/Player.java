package player;

import gamecomponents.Card;
import gamecomponents.Country;
import gamecomponents.Deck;
import gamecomponents.GameData;
import ui.Map;

import java.awt.*;
import java.lang.reflect.Array;
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
    public static void assignCountriesControlled(ActivePlayer[] activePlayers, PassivePlayer[] passivePlayers, Map map)
    {
        ArrayList<Integer> countryIndexes = new ArrayList<Integer>();
        for(int i = 0; i < 42; i++)
        {
            countryIndexes.add(i);
        }

        // randomizes order of 42 integers labelled 0 - 41
        Collections.shuffle(countryIndexes);

        // assigns random countries to each player using randomIndexes to reference the country ArrayList

        int j = 0;
        for(ActivePlayer a: activePlayers)
        {
            for(int i = 0; i < GameData.INIT_COUNTRIES_PLAYER; i++, j++)
            {
                int currentJ = countryIndexes.get(j);
                a.getCountriesControlled().add(map.getCountries().get(currentJ).getName(), map.getCountries().get(currentJ));
                a.getCountriesControlled().get(i).setControlledBy(a);
            }
        }

        for(PassivePlayer p: passivePlayers)
        {
            for(int i = 0; i < GameData.INIT_COUNTRIES_NEUTRAL; i++, j++)
            {
                int currentJ = countryIndexes.get(j);
                p.getCountriesControlled().add(map.getCountries().get(currentJ).getName(), map.getCountries().get(currentJ));
                p.getCountriesControlled().get(i).setControlledBy(p);
            }
        }
    }
    
    @Override
	public String toString()
	{
		return "[name=" + name + ", playerColor=" + playerColor + "]";
	}
    
	public String printCountries() {
		String output = "[";
		for (Country c : getCountriesControlled()) {
			output = output + c.getName() + ", "; 
		}
		output = output.substring(0, output.length()-2) + "]"; 
		return output;
	}
}
