package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gamecomponents.Game;
import player.Player;

class GameTest {

	Game game = new Game(); 
	
	@Test
	void testConstructor() { //tests that the constructor initialises the array of players
		if (game.getPlayers().length == 0) {
			fail("the array of platers should not be empty");
		}
	}
	
	@Test 
	void testGetterMethod() {
		Player[] players = game.getPlayers();
		assertEquals(players[0].toString(), "[name=, playerColor=BLUE]");
		assertEquals(players[1].toString(), "[name=, playerColor=GREEN]");
		assertEquals(players[2].toString(), "[name=Benny, playerColor=BROWN]");
		assertEquals(players[3].toString(), "[name=Harry, playerColor=GREY]");
		assertEquals(players[4].toString(), "[name=Jolene, playerColor=ORANGE]");
		assertEquals(players[5].toString(), "[name=Borgov, playerColor=YELLOW]");
	}
	
	

}
