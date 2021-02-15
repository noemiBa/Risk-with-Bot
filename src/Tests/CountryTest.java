package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gamecomponents.Country;
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
	void testGetContinentName() {
		Country c = map.getCountries().get(0);
		assertEquals(c.getContinentName(), "N America");
		
		Country c1 = map.getCountries().get(22);
		assertEquals(c1.getContinentName(), "Asia");	
	}
	
	@Test
	void testToString() {
		Country c = map.getCountries().get(0);
		assertEquals(c.toString(), "Country [name=Ontario, coord_x=213, coord_y=170, continent=0, adjCountries=[4, 1, 5, 6, 3, 2]]");
		
		Country c1 = map.getCountries().get(6);
		assertEquals(c1.toString(), "Country [name=W United States, coord_x=153, coord_y=225, continent=0, adjCountries=[3, 0, 5, 7]]");
		
		Country c2 = map.getCountries().get(41);
		assertEquals(c2.toString(), "Country [name=Madagascar, coord_x=528, coord_y=513, continent=5, adjCountries=[38, 40]]");
	}
	
 }
