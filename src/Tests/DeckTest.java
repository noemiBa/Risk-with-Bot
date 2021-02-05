package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import gamecomponents.Card;
import gamecomponents.Deck;

class DeckTest {

	@Test
	void testConstructor() { //test that the constructor initialises the deck of cards
		Deck deck = new Deck();
		if (deck.getCards().isEmpty()) {
			fail("the deck of cards should not be empty");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	void testShuffle() { //checks that the order of the cards changes after calling the shuffle method
		Deck deck = new Deck();
		Deck deck1 = new Deck();
		ArrayList<Card> cards = (ArrayList<Card>) deck.getCards().clone(); 
		ArrayList<Card> cards2 = (ArrayList<Card>) deck1.getCards().clone();
		deck.shuffle(cards); 
		assertFalse(cards.get(0).getCountryName().equals(cards2.get(0).getCountryName()));
	}
	
	@Test 
	void testGetterMethod() {
		Deck deck = new Deck();
		ArrayList<Card> cards = (ArrayList<Card>) deck.getCards();
		assertEquals(cards.get(0).toString(), "[countryName=Ontario, unitType=INFANTRY]");
		assertEquals(cards.get(41).toString(), "[countryName=Madagascar, unitType=INFANTRY]");
	}

}
