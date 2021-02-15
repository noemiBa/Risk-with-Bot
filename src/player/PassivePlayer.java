package player;

import java.awt.*;

/** implements Player interface
 * */
public class PassivePlayer extends Player
{
    public PassivePlayer(String name, Color playerColor)
    {
        super(name, playerColor);
    }
    public static PassivePlayer[] initialisePassivePlayers()
    {
        return new PassivePlayer[]
        {
            new PassivePlayer("Benny", new Color(177, 212, 174)), //pale green
            new PassivePlayer("Harry", new Color(235, 232, 234)), //light grey
            new PassivePlayer("Jolene", new Color(248, 250, 162)), //pale yellow
            new PassivePlayer("Borgov", new Color(237, 181, 126)) //peach
        };
    }
    
}
