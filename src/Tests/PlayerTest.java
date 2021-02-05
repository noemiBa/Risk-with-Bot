package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import player.ActivePlayer;
import player.PassivePlayer;
import player.Player.color;

class PlayerTest {
	/* Note: Player is an abstract class - as such, it cannot be instantiated. For that reason,
	 * instances of ActivePlayer and PassivePlayer (that extend the Player class) will be created in order to test the Class. 
	 */

	@Test
	void testConstructorActivePlayer() {
		ActivePlayer player = new ActivePlayer("player", color.GREEN);
		assertEquals(player.toString(), "[name=player, playerColor=GREEN]");

		//Note: the player class can be initialised by having an empty String as the name
		ActivePlayer player1 = new ActivePlayer("", color.GREEN);
		assertEquals(player1.toString(), "[name=, playerColor=GREEN]");

		//Ensure that the cards arrayList is not null upon construction
		if (player.getCards()==null) {
			fail("The arrayList should have been instantiated");
		}

		if (player1.getCards()==null) {
			fail("The arrayList should have been instantiated");
		}
	}

	@Test
	void testConstructorPassivePlayer() {
		PassivePlayer player = new PassivePlayer("player", color.GREEN);
		assertEquals(player.toString(), "[name=player, playerColor=GREEN]");

		//Note: the player class can be initialised by having an empty String as the name
		PassivePlayer player1 = new PassivePlayer("", color.GREEN);
		assertEquals(player1.toString(), "[name=, playerColor=GREEN]");

		//Ensure that the cards arrayList is not null upon construction
		if (player.getCards()==null) {
			fail("The arrayList should have been instantiated");
		}

		if (player1.getCards()==null) {
			fail("The arrayList should have been instantiated");
		}
	}

	@Test
	void testGetterMethods() {
		ActivePlayer player = new ActivePlayer("player", color.GREEN);
		PassivePlayer player1 = new PassivePlayer("", color.BLUE);

		assertEquals(player.getName(), "player");
		assertEquals(player1.getName(), "");

		assertEquals(player.getPlayerColor(), color.GREEN);
		assertEquals(player1.getPlayerColor(), color.BLUE);
	}
		
	@Test
	void testAssignCountries() {
		ActivePlayer player = new ActivePlayer("player", color.GREEN);
		PassivePlayer player1 = new PassivePlayer("", color.BLUE);
		
		fail("Not yet implemented");
		
	}
}
