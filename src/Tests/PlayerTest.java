//package Tests;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//import com.botharmon.Game;
//
//import gamecomponents.Card;
//import player.ActivePlayer;
//import player.PassivePlayer;
//import player.Player;
//import ui.Map;
//
//import java.awt.*;
//
//class PlayerTest {
//
//	Map map = new Map();
//    ActivePlayer [] activePlayers = ActivePlayer.initialiseActivePlayers();
//    PassivePlayer [] passivePlayers = PassivePlayer.initialisePassivePlayers();
//
//	/* Note: Player is an abstract class - as such, it cannot be instantiated. For that reason,
//	 * instances of ActivePlayer and PassivePlayer (that extend the Player class) will be created in order to test the Class.
//	 */
//
//	@Test
//	void testConstructorActivePlayer() {
//		ActivePlayer player = new ActivePlayer(1,"player", Color.GREEN);
//		assertEquals(player.toString(), "[name=player, playerColor=java.awt.Color[r=0,g=255,b=0]]");
//
//		//Note: the player class can be initialised by having an empty String as the name
//		ActivePlayer player1 = new ActivePlayer(1,"", Color.GREEN);
//		assertEquals(player1.toString(), "[name=, playerColor=java.awt.Color[r=0,g=255,b=0]]");
//
//		//Ensure that the cards arrayList is not null upon construction
//		if (player.getCards()==null) {
//			fail("The arrayList should have been instantiated");
//		}
//
//		if (player1.getCards()==null) {
//			fail("The arrayList should have been instantiated");
//		}
//	}
//
//	@Test
//	void testConstructorPassivePlayer() {
//		PassivePlayer player = new PassivePlayer("player", Color.GREEN);
//		assertEquals(player.toString(), "[name=player, playerColor=java.awt.Color[r=0,g=255,b=0]]");
//
//		//Note: the player class can be initialised by having an empty String as the name
//		PassivePlayer player1 = new PassivePlayer("", Color.GREEN);
//		assertEquals(player1.toString(), "[name=, playerColor=java.awt.Color[r=0,g=255,b=0]]");
//
//		//Ensure that the cards arrayList is not null upon construction
//		if (player.getCards()==null) {
//			fail("The arrayList should have been instantiated");
//		}
//
//		if (player1.getCards()==null) {
//			fail("The arrayList should have been instantiated");
//		}
//	}
//
//	@Test
//	void testGetterMethods() {
//		ActivePlayer player = new ActivePlayer(1,"player", Color.GREEN);
//		PassivePlayer player1 = new PassivePlayer("", Color.BLUE);
//
//		assertEquals(player.getName(), "player");
//		assertEquals(player1.getName(), "");
//
//		assertEquals(player.getPlayerColor(), Color.GREEN);
//		assertEquals(player1.getPlayerColor(), Color.BLUE);
//
//		player.getCards().add(new Card("Italy", type.INFANTRY));
//		player.getCards().add(new Card("Brazil", type.INFANTRY));
//		assertEquals(player.getCards().toString(), "[[countryName=Italy, unitType=INFANTRY], [countryName=Brazil, unitType=INFANTRY]]");
//	}
//
//	@Test
//	void testAssignCountriesControlled() {
//        Player.assignCountriesControlled(activePlayers, passivePlayers, map);
//
//		//Check that the method assignCountries assigned the correct amount of cards i.e. 9 to the Active Player and 6 to the Passive Player
//		assertEquals(activePlayers[0].getCountriesControlled().size(), 9);
//		assertEquals(activePlayers[1].getCountriesControlled().size(), 9);
//		assertEquals(passivePlayers[0].getCountriesControlled().size(), 6);
//		assertEquals(passivePlayers[3].getCountriesControlled().size(), 6);
//
//		//Check that none of the cards are repeated
//		int i = 0;
//		for (Card c : activePlayers[i].getCards()) {
//			if (passivePlayers[i].getCards().get(i).equals(c)) {
//				fail("No cards should be repeated");
//			}
//			i++;
//		}
//		//if we reach this points no cards were repeated.
//	}
//
//	@Test
//	void testToString() {
//		ActivePlayer player = new ActivePlayer(1,"player", Color.GREEN);
//		PassivePlayer passivePlayer = new PassivePlayer("player2", Color.BLUE);
//
//		assertEquals(player.toString(), "[name=player, playerColor=java.awt.Color[r=0,g=255,b=0]]");
//		assertEquals(passivePlayer.toString(), "[name=player2, playerColor=java.awt.Color[r=0,g=0,b=255]]");
//	}
//
//	/* This method is different from the toString() method of Country, as instead of printing all
//	 * the country information, it should just print the country names.
//	 */
//	@Test
//	void printCountries() {
//		//need to manually assign the countries to test the method, as the method assignCountriesControlled() is randomised.
//		activePlayers[0].getCountriesControlled().add("Ontario", map.getCountries().get(0));
//		activePlayers[0].getCountriesControlled().add("Quebec", map.getCountries().get(1));
//
//		assertEquals(activePlayers[0].printCountries(), "[Ontario, Quebec]");
//
//		passivePlayers[0].getCountriesControlled().add("Alberta", map.getCountries().get(3));
//		passivePlayers[0].getCountriesControlled().add("Greenland", map.getCountries().get(4));
//		passivePlayers[0].getCountriesControlled().add("E United States", map.getCountries().get(5));
//
//		assertEquals(passivePlayers[0].printCountries(), "[Alberta, Greenland, E United States]");
//	}
//}
