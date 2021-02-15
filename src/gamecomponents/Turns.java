package gamecomponents;

import com.botharmon.Game;
import player.ActivePlayer;
import ui.Window;

public class Turns
{
    // Can be modified, feel free to change the ENUM values to add, remove, reorder stages of the game
    public enum stage
    {
        ENTER_NAMES,
        ROLL_TO_PLACE_TERRITORIES,
        MAIN_TURN,
        END
    };

    private stage gameStage = stage.ENTER_NAMES;

    // Game ends when gameStage is set to stage.END
    public void nextTurns(Game risk)
    {
        while(gameStage != stage.END)
        {
            makeTurns(risk);
            // for testing purposes
            System.out.println(gameStage);
        }
    }

    // each players turn for each stage is implemented here
    public void makeTurns(Game risk)
    {
        switch(gameStage)
        {
            case ENTER_NAMES:
                enterPlayerNames(risk);
                gameStage = stage.ROLL_TO_PLACE_TERRITORIES;
                break;
            case ROLL_TO_PLACE_TERRITORIES:
                /*
                * may be better to implement player class as a circular ArrayList
                * could also switch player positions in the array to ensure the right player goes first
                */
                int playerWhoStarts = whoStarts(risk.getActivePlayers(), risk.getWindow());
                // Rest of allocating territories implemented here
                gameStage = stage.MAIN_TURN;
                break;
            case MAIN_TURN:
                // Main sequence of turns for both players can be implemented here
                // stage.END can be moved inside turn method with game winning condition to check if the game should end
                gameStage = stage.END;
                break;
            default:
                break;
        }
    }

    public void enterPlayerNames(Game risk)
    {
        for(ActivePlayer a: risk.getActivePlayers())
        {
            risk.getWindow().getTextDisplay("<html>Enter player " + a.getPlayerNumber() + "'s name</html>");
            a.setName(risk.getWindow().getCommand());
            risk.getWindow().getTextDisplay("<html>Thanks for that</html>");
        }
        Country.initialiseUnits(risk.getMap().getCountries());
        risk.getDeck().shuffle();
        risk.getWindow().getTextDisplay("<html>We are now ready to start the game: "
        + risk.getActivePlayers()[0].getName() + " and " + risk.getActivePlayers()[1].getName() + "</html>");
    }

    public int whoStarts(ActivePlayer[] activePlayers, Window window)
    {
        int diceFirstPlayer = activePlayers[0].throwDice();
        int diceSecondPlayer = activePlayers[1].throwDice();

        while(diceFirstPlayer == diceSecondPlayer)
        {
            diceFirstPlayer = activePlayers[0].throwDice();
            diceSecondPlayer = activePlayers[1].throwDice();
        }

        if(diceFirstPlayer > diceSecondPlayer)
        {
            window.getTextDisplay("<html>" + activePlayers[0].getName() + " starts!</html>");
            return 0;
        }
        else
        {
            window.getTextDisplay("<html>" + activePlayers[1].getName() + " starts!</html>");
            return 1;
        }
    }
}
