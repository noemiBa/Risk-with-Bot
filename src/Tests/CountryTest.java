package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import gamecomponents.Country;
import player.ActivePlayer;
import player.PassivePlayer.color;
import player.Player;
import ui.Map;

class CountryTest {
	
	Map map = new Map();

	@Test
	void testConstructor() {
		Country country = new Country("Italy", 3, 5);
		assertEquals(country.toString(), "Country [name=Italy, coord_x=3, coord_y=5, continent=-1, adjCountries=[]]");
		
		Country country1 = new Country("Brazil", 10, 10);
		assertEquals(country1.toString(), "Country [name=Brazil, coord_x=10, coord_y=10, continent=-1, adjCountries=[]]");
	}
	
	@Test
	void testConstructorErroneousInput() {
		try {
			Country country = new Country(" ", 20, 23);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}
		
		try {
			Country country = new Country("Ireland", -1, 23);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}
		
		try {
			Country country = new Country("Ireland", 5, -20);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}
	}
	
	@Test
	void testGetIndex() {
		assertEquals(Country.getIndex("Ontario"), 0);
		assertEquals(Country.getIndex("Quebec"), 1);
		assertEquals(Country.getIndex("Madagascar"), 41);
	}
	
	@Test 
	void testGetterMethods() {
		map.initialiseCountries(); 
		Country c = map.countries.get(0);
		assertEquals(c.getName(), "Ontario");
		assertEquals(c.getCoord_x(), 213);
		assertEquals(c.getCoord_y(), 170);
		assertEquals(c.getContinent(), 0);
		assertEquals(c.getAdjCountries().toString(), "[4, 1, 5, 6, 3, 2]");
		assertEquals(c.getNumberOfInfantry(), 0);
		assertEquals(c.getNumberOfArtillery(), 0);
		assertEquals(c.getNumberOfCavalry(), 0);
	}
	
	@Test
	void testSetterMethods() {
		map.initialiseCountries(); 
		Country c = map.countries.get(0);
		c.setNumberOfInfantry(4);
		assertEquals(c.getNumberOfInfantry(), 4);
		
		c.setNumberOfArtillery(5);
		assertEquals(c.getNumberOfArtillery(), 5);
		
		c.setNumberOfCavalry(1);
		assertEquals(c.getNumberOfCavalry(), 1);
		
		ArrayList<Integer> adjCountries = new ArrayList<Integer>();
		adjCountries.add(0);
		c.setAdjCountries(adjCountries);
		assertEquals(c.getAdjCountries().toString(), "[0]");
		
		c.setControlledBy(new ActivePlayer("test", color.BLUE));
		assertEquals(c.getControlledBy().toString(), "[name=test, playerColor=BLUE]");
	}
	
	@Test
	void testSetterMethodsErroneousInput() {
		try {
			Country country = new Country("Country", 20, 23);
			country.setNumberOfInfantry(-2);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}
		
		try {
			Country country = new Country("Country", 20, 23);
			country.setNumberOfCavalry(-1);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}
		
		try {
			Country country = new Country("Country", 20, 23);
			country.setNumberOfArtillery(-20);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}
	}
	
	@Test
	void testGetContinentName() {
		map.initialiseCountries(); 
		Country c = map.countries.get(0);
		assertEquals(c.getContinentName(), "N America");
		
		Country c1 = map.countries.get(22);
		assertEquals(c1.getContinentName(), "Asia");	
	}
	
	@Test
	void testToString() {
		map.initialiseCountries(); 
		Country c = map.countries.get(0);
		assertEquals(c.toString(), "Country [name=Ontario, coord_x=213, coord_y=170, continent=0, adjCountries=[4, 1, 5, 6, 3, 2]]");
		
		Country c1 = map.countries.get(6);
		assertEquals(c1.toString(), "Country [name=W United States, coord_x=153, coord_y=225, continent=0, adjCountries=[3, 0, 5, 7]]");
		
		Country c2 = map.countries.get(41);
		assertEquals(c2.toString(), "Country [name=Madagascar, coord_x=528, coord_y=513, continent=5, adjCountries=[38, 40]]");
	}
	
 }
