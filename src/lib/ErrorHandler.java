package lib;

import com.botharmon.Game;

import gamecomponents.Country;
import player.ActivePlayer;
import player.Player;
import ui.Window;

public class ErrorHandler {
	 Window window;
	 
	 public ErrorHandler(Window window) {
		 this.window = window; 
	 }
	 
	/* method displays an error message if the player has entered an invalid input as their name.
    *
    * @param risk: the game currently being played
    * @param playerName: the input entered by the user
    * @return the valid playerName
    */
   public String validatePlayerName(String playerName) {
       //while the player name is an empty String or a number
       while (playerName.trim().isEmpty() || playerName.matches("[0-9]+")) {
           window.sendErrorMessage("That doesn't quite look like a name, try again!");
           playerName = window.getCommand(); //send an error message and take and discard the input
       }
       return playerName;
   }

   /* method asks the second player to enter a different name than player one
    *
    * @param numberToAdd: array of active players, the input entered by the user and game instance
    * @return the valid player name
    */
   public String validateDifferentName(ActivePlayer[] activePlayers, String playerName, Game risk) {
       while (activePlayers[1].getName().equals(activePlayers[0].getName())) {
           window.getTextDisplay("Looks like you guys have the same name, what a coincidence! First Player arrived first, sorry!\n" +
                   "Second Player enter a new name: ");
           playerName = risk.getWindow().getCommand();
           risk.getActivePlayers()[1].setName(validatePlayerName(playerName));
       }
       return playerName;
   }

   /* displays an error message if the player has entered an invalid input as the number of Units to Add.
    *
    * @param numberToAdd: the input entered by the user
    * @return the valid numberToAdd
    */
   public int validateNoUnits(int numberToAdd, int numberUnitsLeft, String countryName) {
	if (numberToAdd == -1) { //numberToAdd is initialised as -1. If by the time this method is called numberToAdd is still -1, there was an issue with the user inp
           window.sendErrorMessage("You forgot to type the units you want to allocate to " + countryName + "\nEnter the number: ");
           return -1;
       } else if (numberToAdd > 3 || numberToAdd < 1) {
           window.sendErrorMessage("That is invalid, please enter a number between 1-3 to add to " + countryName);
           return -1;
       } else if (numberToAdd > numberUnitsLeft) {
           window.sendErrorMessage("You don't have that many units left! You only have " + numberUnitsLeft + " enter a new valid number to add to " + countryName);
           return -1;
       }
       window.clearText();
       return numberToAdd;
   }

   /* displays an error message if the player has entered an invalid input (or ambiguous name) as country to add units to.
    *
    * @param countryName: the input entered by the user
    * @return the valid countryName
    */
   public String validateCountry(String countryName, Player player) {

       CustomArrayList<Country> countries = player.getCountriesControlled();

       while (countries.get(countryName) == null) {
           window.sendErrorMessage("Sorry, it looks like " + player.getName() + " does not own this country or you typed it wrong!"
                   + "\nEnter a country of " + player.getName() + "'s colour");
           countryName = TextParser.parse(window.getCommand().trim());
       }

       window.clearText();
       return countryName;
   }
}
