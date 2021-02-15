package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.botharmon.Game;

class GameTest {

	@Test
	void testConstructor() { //tests that the constructor initialises the array of players
		Game game = new Game();
//		if (game.getPlayers().length == 0) {
			fail("the array of platers should not be empty");
//		}
	}
}
