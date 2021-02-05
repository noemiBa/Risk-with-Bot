package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gamecomponents.Card;
import gamecomponents.Card.type;
import gamecomponents.Deck;
import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;
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
		
		player.getCards().add(new Card("Italy", type.INFANTRY));
		player.getCards().add(new Card("Brazil", type.INFANTRY));
		assertEquals(player.getCards().toString(), "[[countryName=Italy, unitType=INFANTRY], [countryName=Brazil, unitType=INFANTRY]]");
	}
		
	@Test
	void testAssignCountries() {
		ActivePlayer activePlayer = new ActivePlayer("player", color.GREEN);
		PassivePlayer passivePlayer = new PassivePlayer("", color.BLUE);
	
		Player[] players= {(Player) activePlayer, (Player) passivePlayer};
		Deck deck = new Deck();
		
		Player.assignCountries(players, deck); 
		
		//Check that the method assignCountries assigned the correct amount of cards i.e. 9 to the Active Player and 6 to the Passive Player
		assertEquals(activePlayer.getCards().size(), 9);
		assertEquals(passivePlayer.getCards().size(), 6);
		
		//Check that none of the cards are repeated 
		int i = 0; 
		for (Card c : activePlayer.getCards()) {
			if (passivePlayer.getCards().get(i).equals(c)) {
				fail("No cards should be repeated");
			}
		}
		//if we reach this points no cards were repeated.
	}
	
	@Test
	void testToString() {
		ActivePlayer player = new ActivePlayer("player", color.GREEN);
		PassivePlayer passivePlayer = new PassivePlayer("player2", color.BLUE);
		assertEquals(player.toString(), "[name=player, playerColor=GREEN]");
		assertEquals(passivePlayer.toString(), "[name=player2, playerColor=BLUE]");
	}
}
