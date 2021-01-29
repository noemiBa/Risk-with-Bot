package risk;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Country {
	private String name; 
	private int coord_x;
	private int coord_y;
	private int continent;
	private ArrayList<Integer> adjCountries;
	public static final String[] CONTINENT_NAMES = {"N America","Europe","Asia","Australia","S America","Africa"};  
	public static final int[] CONTINENT_VALUES = {5,5,7,2,2,3};
	
	public Country(String name, int coord_x, int coord_y) {
		this.name = name; 
		this.coord_x = coord_x; 
		this.coord_y = coord_y;
		this.continent = -1;
		this.adjCountries = new ArrayList<Integer>(); 
	}

	public int getContinent() {
		return continent;
	}
	
	public ArrayList<Integer> getAdjCountries() {
		return adjCountries;
	}

	public void setAdjCountries(ArrayList<Integer> adjCountries) {
		this.adjCountries = adjCountries;
	}
	
	public int getAdjCountriesLength() {
		return adjCountries.size();
	}

	public void setContinent(int contintent) {
		this.continent = contintent;
	}
	
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

	public String getName() {
		return name;
	}

	public int getCoord_x() {
		return coord_x;
	}

	public void setCoord_x(int coord_x) {
		this.coord_x = coord_x;
	}

	public int getCoord_y() {
		return coord_y;
	}

	public void setCoord_y(int coord_y) {
		this.coord_y = coord_y;
	}
	
	/*
	 * For testing purposes
	 */
	@Override
	public String toString() {
		return "Country [name=" + name + ", coord_x=" + coord_x + ", coord_y=" + coord_y + ", continent=" + continent
				+ ", adjCountries=" + adjCountries + "]";
	}

}
