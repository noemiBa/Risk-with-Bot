package Tests;

//JUnit imports
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

//botHarmon imports
import gamecomponents.Country;
import player.*;
import ui.Map;

class CountryTest {
	
	Map map = new Map();
	Player player = new ActivePlayer(0, "Dumbledore", Color.blue); 

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
	void TestGetterMethods() {
		Country country = new Country("Ireland", 5, 20);
		country.setContinent(0); 
		country.setNumberOfUnits(4);
		country.setControlledBy(player);
		country.setAdjCountriesIndexes(new ArrayList<Integer>());
		
		assertEquals(country.getName(), "Ireland");
		assertEquals(country.getCoord_x(), 5); 
		assertEquals(country.getCoord_y(), 20); 
		assertEquals(country.getContinent(), 0);
		assertEquals(country.getNumberOfUnits(), 4);
		assertEquals(country.getControlledBy(), player);
		assertEquals(country.getAdjCountriesIndexes().toString(), "[]");
		
	}
	
	@Test
	void testSetterMethods() { 
		Country c = new Country("Ireland", 0, 0);
		c.setNumberOfUnits(4);
		assertEquals(c.getNumberOfUnits(), 4);

		c.setNumberOfUnits(5);
		assertEquals(c.getNumberOfUnits(), 5);

		c.setNumberOfUnits(1);
		assertEquals(c.getNumberOfUnits(), 1);

		ArrayList<Integer> adjCountries = new ArrayList<Integer>();
		adjCountries.add(0);
		c.setAdjCountriesIndexes(adjCountries);
		assertEquals(c.getAdjCountriesIndexes().toString(), "[0]");

		c.setControlledBy(player);
		assertEquals(c.getControlledBy().toString(), "[name=Dumbledore, playerColor=java.awt.Color[r=0,g=0,b=255]]");
	}

	@Test
	void testSetterMethodsErroneousInput() {
		try {
			Country country = new Country("Country", 20, 23);
			country.setNumberOfUnits(-2);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}

		try {
			Country country = new Country("Country", 20, 23);
			country.setNumberOfUnits(-1);
			fail("Should throw an exception");
		}
		catch (IllegalArgumentException ex) {}

		try {
			Country country = new Country("Country", 20, 23);
			country.setNumberOfUnits(-20);
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
	void testGetContinentName() {
		Country c = map.getCountries().get(0);
		assertEquals(c.getContinentName(), "N America");
		
		Country c1 = map.getCountries().get(22);
		assertEquals(c1.getContinentName(), "Asia");	
	}
	
	@Test
	void testToString() {
		Country c = map.getCountries().get(0);
		assertEquals(c.toString(), "Country [name=Ontario, coord_x=213, coord_y=130, continent=0, adjCountries=[4, 1, 5, 6, 3, 2]]");
		
		Country c1 = map.getCountries().get(6);
		assertEquals(c1.toString(), "Country [name=W United States, coord_x=153, coord_y=185, continent=0, adjCountries=[3, 0, 5, 7]]");
		
		Country c2 = map.getCountries().get(41);
		assertEquals(c2.toString(), "Country [name=Madagascar, coord_x=528, coord_y=473, continent=5, adjCountries=[38, 40]]");
	}
	
	@Test
	void testInitialiseUnits() {
		 Country.initialiseUnits(map.getCountries());
		 
		 for (Country c : map.getCountries()) {
			 assertEquals(c.getNumberOfUnits(), 1);
		 }
	}
	
 }
