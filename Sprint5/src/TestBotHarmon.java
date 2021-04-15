import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBotHarmon {
    private Board board;
    private Player player;
    private  Bot bot;
    private Deck deck;

    @BeforeEach
    void setUp() {
        board = new Board();
        deck = new Deck(Deck.NO_WILD_CARD_DECK);
        player = player = new Player(0);
        for(int i = 0; i < 5; i++) {
            player.addCard(deck.getCard());
        }
        BotHarmon bot = new BotHarmon(board, player);
        player.setBot(bot);

    }

    @Test
    void testGetCardExchange() {
        String[] setOfCards = new String[]{"iii", "ccc", "aaa", "ica", "iac", "aci", "aic", "cia", "cai"};
        String exchange = player.getBot().getCardExchange();
        boolean isFound = false;
        for(String s: setOfCards) {
            if(exchange.equals(s)) {
                isFound = true;
                break;
            }
        }
        assertTrue(isFound);
    }
}
