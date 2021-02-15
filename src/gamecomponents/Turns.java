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
        ASSIGN_COUNTRIES,
        ROLL_TO_PLACE_TERRITORIES,
        MAIN_TURN,
        END
    };

    private stage gameStage = stage.ENTER_NAMES;

    public stage getGameStage()
    {
        return gameStage;
    }
    
    public void setGameStage(stage gameStage) {
		this.gameStage = gameStage;
	}

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
                setGameStage(stage.ASSIGN_COUNTRIES);
                risk.getMap().updateUI(this.gameStage);
                break;
            case ASSIGN_COUNTRIES: 
            	 assignCountries(risk);
            	 setGameStage(stage.ROLL_TO_PLACE_TERRITORIES);
            	 break; 
            case ROLL_TO_PLACE_TERRITORIES:
                /*
                * may be better to implement player class as a circular ArrayList
                * could also switch player positions in the array to ensure the right player goes first
                */
                int playerWhoStarts = whoStarts(risk.getActivePlayers(), risk.getWindow());
                // Allocating reinforcements implemented here
                setGameStage(stage.MAIN_TURN);
                break;
            case MAIN_TURN:
                // Main sequence of turns for both players can be implemented here
                // stage.END can be moved inside turn method with game winning condition to check if the game should end
            	setGameStage(stage.END);
                break;
            default:
                break;
        }
    }

	public void enterPlayerNames(Game risk)
    {
        for(ActivePlayer a: risk.getActivePlayers())
        {
            risk.getWindow().getTextDisplay("Welcome, player " + a.getPlayerNumber() + "! Enter your name: ");
            a.setName(risk.getWindow().getCommand());
        } 
       
        //Assign Players their colours
        risk.getWindow().getTextDisplay(risk.getActivePlayers()[0].getName() + " your color is Cyan");
        risk.getWindow().getTextDisplay(risk.getActivePlayers()[1].getName() + " your color is Pink");
    }
    
    public void assignCountries(Game risk) {
        Country.initialiseUnits(risk.getMap().getCountries());
        
        risk.getWindow().getTextDisplay(risk.getActivePlayers()[0].getName() + ", you drew the Countries: " + risk.getActivePlayers()[0].printCountries());
        risk.getWindow().getTextDisplay(risk.getActivePlayers()[1].getName() + ", you drew the Countries: " + risk.getActivePlayers()[1].printCountries());
        
        risk.getDeck().shuffle();
        risk.getWindow().getTextDisplay("Great, we are now ready to start the game!");
        
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
            window.getTextDisplay(activePlayers[0].getName() + "starts!");
            return 0;
        }
        else
        {
            window.getTextDisplay(activePlayers[1].getName() + " starts!");
            return 1;
        }
    }
}
