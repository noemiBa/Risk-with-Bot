package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gamecomponents.Country;
import ui.Map;

class MapTest {

	@Test
	void testConstructor() {
		Map map = new Map();
		
		//test that the countries arrayList is initialised when an instance of the class Map is created
		if (map.getCountries() == null) {
			fail("The countries array should have been initialised");
		}
	}
	
	@Test 
	void testInitialiseCountries() {

		//Check that the countries arrayList has been initialised and given the correct 1) name, 2) X coordinates, 3) Y coordinates, 4) continent it belongs to, 5) arrayList of adjacent countries indexes
		Map map = new Map();

		Country c = map.getCountries().get(14);
		assertEquals(c.toString(), "Country [name=Iceland, coord_x=373, coord_y=160, continent=1, adjCountries=[15, 9, 4]]");
		
		Country c1 = map.getCountries().get(27);
		assertEquals(c1.toString(), "Country [name=China, coord_x=665, coord_y=290, continent=2, adjCountries=[26, 23, 17, 16, 20, 25]]");
		
		Country c2 = map.getCountries().get(41);
		assertEquals(c2.toString(), "Country [name=Madagascar, coord_x=528, coord_y=513, continent=5, adjCountries=[38, 40]]");
	}

}
