package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import gamecomponents.Deck;
import player.ActivePlayer;

class ActivePlayerTest {
	ActivePlayer[] activePlayers = ActivePlayer.initialiseActivePlayers(); 
	Deck deck = new Deck(); 
	
	@Test
	void testInitialiseActivePlayers() {
		//activePlayer names should be initialised as an empty String
		assertEquals(activePlayers[0].getName(), "");
		assertEquals(activePlayers[1].getName(), "");
		
		assertEquals(activePlayers[0].getPlayerColor().toString(), "java.awt.Color[r=47,g=206,b=237]");
		assertEquals(activePlayers[1].getPlayerColor().toString(), "java.awt.Color[r=199,g=60,b=194]");
		
		assertEquals(activePlayers[0].getPlayerNumber(), 1);
		assertEquals(activePlayers[1].getPlayerNumber(), 2);
	}
	
	@Test
	void testGetPlayerNumber() {
		ActivePlayer player = new ActivePlayer(7, "", Color.green);
		ActivePlayer player1 = new ActivePlayer(12, "", Color.green);
		
		assertEquals(player.getPlayerNumber(), 7);
		assertEquals(player1.getPlayerNumber(), 12);
	}
	
	@Test
	void testGetPlayerNumberErroneousInput() {
		try {
		ActivePlayer player = new ActivePlayer(0, "", Color.green);
		fail("Should have thrown an exception");
		}
		catch (IllegalArgumentException ex) {} 
		
		try {
			ActivePlayer player = new ActivePlayer(-7, "", Color.green);
			fail("Should have thrown an exception");
		}
		catch (IllegalArgumentException ex) {} 
	}
	
	@Test 
	void testDraw() {
		//upon initialisation, the number of cards should be 0.
		assertEquals(activePlayers[0].getCards().size(), 0);
		
		activePlayers[0].draw(activePlayers[0].getCards().size(), deck); 
		activePlayers[0].draw(activePlayers[0].getCards().size(), deck); 
		activePlayers[0].draw(activePlayers[0].getCards().size(), deck); 
		activePlayers[0].draw(activePlayers[0].getCards().size(), deck); 
		
		//assertEquals(activePlayers[0].getCards().size(), 4);
	}
	
	@Test
	void testThrowDice() {
		ActivePlayer p = new ActivePlayer(1, "", Color.green);
		//ensure that the number rolled in not smaller than 1 or larger than 6
		assertTrue(p.throwDice()>=1 && p.throwDice()<=6);
		assertTrue(p.throwDice()>=1 && p.throwDice()<=6);
		assertTrue(p.throwDice()>=1 && p.throwDice()<=6);
		assertTrue(p.throwDice()>=1 && p.throwDice()<=6);
		assertTrue(p.throwDice()>=1 && p.throwDice()<=6);
	}
}
