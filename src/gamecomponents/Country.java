package gamecomponents;

import lib.CustomArrayList;
import player.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import ui.*;

import javax.swing.*;

public class Country {
    //declaration of instance variables
    private String name;
    private int coord_x;
    private int coord_y;
    private int continent;
    private Player controlledBy;
    private int numberOfUnits;
    private CustomArrayList<Country> adjCountries;
    private ArrayList<Integer> adjCountriesIndexes;

    /** Constructor: constructs the object Country. Here the continent index is set to -1 to start, and the arrayList of adjacent countries initialised.
     * @param name: the name of the country
     * @param coord_x: the x coordinate of the country on the Map
     * @param coord_y: the y coordinate of the country on the Map
     */
    public Country(String name, int coord_x, int coord_y) {
    	validateCountry(name); 
    	validateCountry(coord_x); 
    	validateCountry(coord_y);
        this.name = name;
        this.coord_x = coord_x;
        this.coord_y = coord_y;
        this.continent = -1;
        this.adjCountriesIndexes = new ArrayList<Integer>();
        this.adjCountries = new CustomArrayList<Country>();
        this.numberOfUnits = 0;
    }
    
    /* Utility method that returns the index of a Country if given the name of said Country
     * @param name: the name of the Country
     */
    public static int getIndex(String countryName) {
    	int index = -1; 
    	for (int i = 0; i < GameData.COUNTRY_NAMES.length; i++) {
    		if (countryName.equals(GameData.COUNTRY_NAMES[i])) {
    			index = i; 
    		}
    	}
    	return index; 
    }

    public int getNumberOfUnits() {
    	return numberOfUnits;
    }

    /*
     * ACCESSOR methods for instance variables
     */
    public String getName() {
        return name;
    }

    public int getCoord_x() {
        return coord_x;
    }

    public int getContinent() {
        return continent;
    }

    public Player getControlledBy()
    {
        return controlledBy;
    }

    public Country getAdjCountry(int i)
    {
        return adjCountries.get(i);
    }

    public Country getAdjCountry(String identifier)
    {
        return adjCountries.get(identifier);
    }

    public CustomArrayList<Country> getAdjCountries()
    {
        return adjCountries;
    }

    public ArrayList<Integer> getAdjCountriesIndexes() {
        return adjCountriesIndexes;
    }

    public int getAdjCountriesLength() {
        return adjCountriesIndexes.size();
    }

    public int getCoord_y() {
        return coord_y;
    }
    
    	
    /*
     * MUTATOR methods for instance variables
     */
    public void setNumberOfUnits(int numberOfUnits)  {
    	validateUnits(numberOfUnits);
        this.numberOfUnits = numberOfUnits;
    }

    public void setAdjCountriesIndexes(ArrayList<Integer> adjCountries) {
        this.adjCountriesIndexes = adjCountries;
    }

    public void setContinent(int contintent) {
        this.continent = contintent;
    }
    
    public void setControlledBy(Player controlledBy)
    {
        this.controlledBy = controlledBy;
    }

    public static void initialiseUnits(CustomArrayList<Country> countries)
    {
        for (Country c: countries)
        {
            c.setNumberOfUnits(1);
        }
    }

    /**
     * This method returns the String name of the Continent the Country belongs to.
     */
    public String getContinentName() {
        switch (getContinent()) {
            case 0: return GameData.CONTINENT_NAMES[0];
            case 1: return GameData.CONTINENT_NAMES[1];
            case 2: return GameData.CONTINENT_NAMES[2];
            case 3: return GameData.CONTINENT_NAMES[3];
            case 4: return GameData.CONTINENT_NAMES[4];
            case 5: return GameData.CONTINENT_NAMES[5];
            default: return "Invalid";
        }
    }
    
    /*
     * For testing purposes. Returns a String representation of the Country class.
     */
    @Override
    public String toString() {
        return "Country [name=" + name + ", coord_x=" + coord_x + ", coord_y=" + coord_y + ", continent=" + continent
                + ", adjCountries=" + adjCountriesIndexes + "]";
    }
    
    /* private methods used for exception handling.
     */
    private void validateCountry(String name) {
    	if (name.trim().isEmpty()) {
    		throw new IllegalArgumentException("A country needs a name");
    	}
    }
    
    private void validateCountry(int coord) {
    	if (coord<0) {
    		throw new IllegalArgumentException("Coordinates should not be negative.");
    	}
    }
    
    private void validateUnits(int units) {
    	if (units < 0) {
    		throw new IllegalArgumentException("Number of units should not be negative.");
    	}
    }

}

