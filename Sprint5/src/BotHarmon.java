// put your code here

public class BotHarmon implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example
	
	private BoardAPI board;
	private PlayerAPI player;
	
	BotHarmon(BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		// put your code here
		return;
	}
	
	public String getName () {
		String command = "";
		// put your code here
		command = "BOT";
		return(command);
	}

	public String getReinforcement () {
		String command = "";
		/* Step 1. find all of your territories
		* for each territory : territories owned
		* 1. check adjectent territories to see how many 
		*    units they have.
		* 2. check if country is beside activePlayer country
		*    (+ 1 for each activePlayer country).
		* 3. sum no. units of adjecent countries --> choose 		*    the ones w/ less (easier to attack).
		* 4. (if you can split the reineforcement --> 30% of 
		*    reinforcements units on the frontline).
		*/
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		command += " 1";
		return(command);
	}
	
	public String getPlacement (int forPlayer) {
		String command = "";
		// put your code here
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		return(command);
	}
	
	public String getCardExchange () {
		String command = "";
		// put your code here
		// find out best possible combination using cards 
		// the bot currently possesses
		command = "skip";
		return(command);
	}

	public String getBattle () {
		String command = "";
		// put your code here
		/* 1. find all possible territories to attack [] 
		* for each of those territories, assign points 			* according to:
		* 1. can I break up a continent?
		* 2. can I attack the the activePlayer?
		* 3. can I control a continent?
		* 4. Are most of my troops in this region?
		* 5. Did I get a card already?
		* --> choose to attack territory w/ highest points
		* check tot number of troops you vs your opponent to 
		* to determine whether to attack.
		*/
		command = "skip";
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		/* check if you can defend w/ 2 dices 
		* if you can, GO FOR IT ! 
		* Else, do NOT ;))))
		*/
		command = "1";
		return(command);
	}

	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
		// WHAT DO U WANT ???
		command = "0";
		return(command);
	}

	public String getFortify () {
		String command = "";
		/* Step 1. find all of the countries u control w/ 		* more than 1 unit. 
		* if no countrys > 1 troop --> return "skip"
		* Step 2. find all the connected countries. 
		* --> [] possible movements 
		* for each possible movement, assign points 			* according to: 
		* 1) Do I own a continent? (fortify border)
		*    AND not own countries adjectent to the border
		*    FIND THE FRONT-LINE.
		* 2) is the to-country close to an enemy's  			*    country ?
		* 3) is the to-country close to an activePlayer's
		*    country w/ 1 troop? (DESTROY IT!)
		* 4) is the to-country close to an activePlayer's 
		*    where the difference between units is high -
		*        check difference compared to units I'm 
		*        moving (< 2 -> do it) 
		* Add up all the points, choose movement w/ highest 		* points. 
		*/
		command = "skip";
		return(command);
	}

}
