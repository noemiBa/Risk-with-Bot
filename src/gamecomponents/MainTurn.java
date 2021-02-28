package gamecomponents;

import lib.TextParser;
import player.ActivePlayer;

public class MainTurn  extends Turns{
	  // methods for each turn are called here
    public MainTurn(ActivePlayer activePlayer)
    {
        reinforcementsAllocation(activePlayer);
        // other mainTurn methods go here
        // any method called after the attack method will need to have an if check to check if gameStage has been set to END
        window.clearText();
    }

    public void reinforcementsAllocation(ActivePlayer activePlayer) {
        int numberOfReinforcements = numberOfReinforcements(activePlayer);
        while (numberOfReinforcements != 0) {
            window.getTextDisplay(activePlayer.getName() + ", you have " + numberOfReinforcements + (numberOfReinforcements == 1 ? " reinforcement." : " reinforcements ")
                    + "to place. Please enter a country name belonging to you or a shortened version and the number of troops you want to allocate separated by space, " +
                     "then press enter");
            String countryName = "";
            int numberToAdd = -1;

            String[] input = window.getCommand().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); //splits the string between letters and digits
            input[0] = input[0].trim();
            e.validateCountryAndUnitsEntered(input);
                try {
                    activePlayer.getCountriesControlled().get(input[0]);
                    countryName = TextParser.parse(input[0]);
                    numberToAdd = Integer.parseInt(input[1]);
                    if (numberToAdd > numberOfReinforcements || numberToAdd < 1) {
                        window.clearText();
                        window.sendErrorMessage("You can enter at most " + numberOfReinforcements + " for this allocation");
                    } else {
                        activePlayer.getCountriesControlled().get(countryName).setNumberOfUnits(activePlayer.getCountriesControlled()
                                .get(countryName).getNumberOfUnits() + numberToAdd);
                        numberOfReinforcements = numberOfReinforcements - numberToAdd;
                        window.updateMap();
                        window.clearText();
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    window.sendErrorMessage("You entered the number or country name incorrectly");
                }
                System.out.println(numberOfReinforcements);
            }
    }

    public int numberOfReinforcements(ActivePlayer activePlayer) {
        //3 is the minimum by the rules
        if (activePlayer.getCountriesControlled().size() < 6) {
            return 3;
        } else {
            return activePlayer.getCountriesControlled().size() / 2;
        }
    }

}
