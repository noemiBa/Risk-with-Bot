//package Tests;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import gamecomponents.Card;
//import gamecomponents.Country;
//
//class CardTest {
//
//	@Test
//	void testConstructor() {
//		Card c = new Card("Italy", Card.type.INFANTRY);
//		assertEquals(c.toString(), "[countryName=Italy, unitType=INFANTRY]");
//
//		Card c1 = new Card("Brazil", Card.type.CAVALRY);
//		assertEquals(c1.toString(), "[countryName=Brazil, unitType=CAVALRY]");
//	}
//
//	@Test
//	void testGetterMethods() {
//		Card c = new Card("Italy", Card.type.INFANTRY);
//		assertEquals(c.getCountryName(), "Italy");
//		assertEquals(c.getUnitType(), Card.type.INFANTRY);
//
//		Card c1 = new Card("Brazil", Card.type.CAVALRY);
//		assertEquals(c1.getCountryName(), "Brazil");
//		assertEquals(c1.getUnitType(), Card.type.CAVALRY);
//	}
//
//	@Test
//	void testSetterMethods() {
//		Card c = new Card("Italy", Card.type.INFANTRY);
//		c.setCountryName("NewName");
//		c.setUnitType(Card.type.ARTILLERY);
//		assertEquals(c.getCountryName(), "NewName");
//		assertEquals(c.getUnitType(), Card.type.ARTILLERY);
//	}
//
//	@Test
//	void testSetterMethodsErroneousInput() {
//		Card c = new Card("Italy", Card.type.INFANTRY);
//		try {
//			c.setCountryName("");
//			fail("Should have thrown an exception");
//		}
//		catch (IllegalArgumentException ex) {}
//
//		try {
//			c.setCountryName("   ");
//			fail("Should have thrown an exception");
//		}
//		catch (IllegalArgumentException ex) {}
//	}
//
//	@Test
//	void testToString() {
//		Card c = new Card("Italy", Card.type.INFANTRY);
//		Card c1 = new Card("Brazil", Card.type.CAVALRY);
//		assertEquals(c.toString(), "[countryName=Italy, unitType=INFANTRY]");
//		assertEquals(c1.toString(), "[countryName=Brazil, unitType=CAVALRY]");
//	}
//
//}
