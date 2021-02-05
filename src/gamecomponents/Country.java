	package gamecomponents;

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
    private int numberOfInfantry;
    private int numberOfCavalry;
    private int numberOfArtillery;
    private ArrayList<Integer> adjCountries;
    public static final String[] CONTINENT_NAMES = {"N America","Europe","Asia","Australia","S America","Africa"};
    public static final int[] CONTINENT_VALUES = {5,5,7,2,2,3};

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
        this.adjCountries = new ArrayList<Integer>();
        this.numberOfInfantry = 0; 
        this.numberOfCavalry = 0; 
        this.numberOfArtillery = 0; 
    }
    
    /* Utility method that returns the index of a Country if given the name of said Country
     * @param name: the name of the Country
     */
    public static int getIndex(String countryName) {
    	int index = -1; 
    	for (int i = 0; i < Map.getCountryNames().length; i++) {
    		if (countryName.equals(Map.getCountryNames()[i])) {
    			index = i; 
    		}
    	}
    	return index; 
    }
    
    /* Method returns the int number of units in a Country. Note: 1 infantry = 1 military units, 1 cavalry = 5 military units and 
     * 1 artillery = 10 military units. 
     */
    public int getNumberOfUnits() {
    	return 1*getNumberOfInfantry() + 5*getNumberOfCavalry() + 10*getNumberOfArtillery();
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

    public int getNumberOfInfantry()
    {
        return numberOfInfantry;
    }

    public int getNumberOfCavalry()
    {
        return numberOfCavalry;
    }

    public int getNumberOfArtillery()
    {
        return numberOfArtillery;
    }

    public ArrayList<Integer> getAdjCountries() {
        return adjCountries;
    }

    public int getAdjCountriesLength() {
        return adjCountries.size();
    }

    public int getCoord_y() {
        return coord_y;
    }
    
    	
    /*
     * MUTATOR methods for instance variables
     */
    public void setNumberOfInfantry(int numberOfInfantry)  {
    	validateUnits(numberOfInfantry);
        this.numberOfInfantry = numberOfInfantry;
    }

    public void setNumberOfCavalry(int numberOfCavalry) {
    	validateUnits(numberOfCavalry);
        this.numberOfCavalry = numberOfCavalry;
    }
    
    public void setNumberOfArtillery(int numberOfArtillery) {
    	validateUnits(numberOfArtillery);
        this.numberOfArtillery = numberOfArtillery;
    }
    
    public void setAdjCountries(ArrayList<Integer> adjCountries) {
        this.adjCountries = adjCountries;
    }

    public void setContinent(int contintent) {
        this.continent = contintent;
    }
    
    public void setControlledBy(Player controlledBy)
    {
        this.controlledBy = controlledBy;
    }

    /**
     * This method returns the String name of the Continent the Country belongs to.
     */
    public String getContinentName() {
        switch (getContinent()) {
            case 0: return CONTINENT_NAMES[0];
            case 1: return CONTINENT_NAMES[1];
            case 2: return CONTINENT_NAMES[2];
            case 3: return CONTINENT_NAMES[3];
            case 4: return CONTINENT_NAMES[4];
            case 5: return CONTINENT_NAMES[5];
            default: return "Invalid";
        }
    }
    
    /*
     * For testing purposes. Returns a String representation of the Country class.
     */
    @Override
    public String toString() {
        return "Country [name=" + name + ", coord_x=" + coord_x + ", coord_y=" + coord_y + ", continent=" + continent
                + ", adjCountries=" + adjCountries + "]";
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

