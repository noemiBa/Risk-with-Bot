import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;


// put your code here

public class BotHarmon implements Bot {
	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example

	private BoardAPI board;
	private PlayerAPI player;
	private int botId;
	private int otherPlayerId;

	BotHarmon(BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		botId = player.getId();
		otherPlayerId = 0;
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
		if(player.getCards().size() < 3)
			return "skip";
		int[] exchangeSet = new int[3];
		char[] insignias = {'i', 'c', 'a'};
		for(int i = 0; i < Deck.SETS.length; i++) {
			if(player.isCardsAvailable(Deck.SETS[i])) {
				exchangeSet = Deck.SETS[i];
				break;
			}
		}
		return "" + insignias[exchangeSet[0]] + insignias[exchangeSet[1]] + insignias[exchangeSet[2]];
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

		//Step 1. find all the countries the bot controls with more than one unit
		ArrayList<Integer> countriesControlledMoreThanUnit = new ArrayList<Integer>();
		for (int id : GameData.CONTINENT_IDS) {
			if ((board.getOccupier(id) == botId) && (board.getNumUnits(id) > 1)) {
				countriesControlledMoreThanUnit.add(id);
			}
		}

		//if none of the countries controlled have more than one unit, cannot fortify - return skip
		if (countriesControlledMoreThanUnit.size() == 0) {
			return command = "skip";
		}

		//Step 2. Find all the countries that are connected to those in step 1 and create a collection of all the possible fortification movements
		ArrayList<int []> possibleMovements = new ArrayList<int []>();
		for (int controlled : countriesControlledMoreThanUnit) {
			for (int id : GameData.CONTINENT_IDS) {
				if (board.isConnected(controlled, id)) {
					int [] countryFromCountryTo = new int [3];
					countryFromCountryTo[0] = controlled; //country from
					countryFromCountryTo[1] = id; 		  //country to 
					//countryFromCountryo[2] is left empty. The weight of the choice is added here. 
					possibleMovements.add(countryFromCountryTo);
				}
			}
		}
		
		//if no movements are possible, cannot fortify - return skip
		if (possibleMovements.size() == 0) {
			return command = "skip";
		}

		//CALCULATE WEIGHT OF EACH CHOICE. 
		
		//1. Determine if one of the possible movements is to a border
		int continentOwnedID = continentOwned(); 
		if (continentOwnedID != -1) {
			ArrayList<Integer> continentBorders = findContinentBorder(continentOwnedID);
			
			//find out whether it is possible to fortify a border. If it is, add +1 weight to that possible movement.
			for (int i = 0; i< possibleMovements.size(); i++) {
				for (int borderID : continentBorders) {
					if (possibleMovements.get(i)[1] == borderID) {
						possibleMovements.get(i)[2]++; //add +1 weight to that countryFrom, countryTo combo
					}
				}
			}
		}
		
		//2. Determine if any of the countries adjacent to the countryTo belong to the other player and if these countries 
		//   only have one unit (easy to defeat). For each of the adjacent enemy countries with one unit, add +1 weight.
		for (int i = 0; i < possibleMovements.size(); i++) {
			ArrayList<Integer> enemyCountriesAdjacentOneUnit = findEnemyCountriesAdjacentOneUnit(possibleMovements.get(i)[1]);
			
			if (enemyCountriesAdjacentOneUnit.size() > 0) {
				int numberOfAdjacentEnemyCountriesOneUnit = enemyCountriesAdjacentOneUnit.size(); 
				//add one to the weight of the possible movement for each of the adjacent enemy countries w/ one unit
				possibleMovements.get(i)[2] = possibleMovements.get(i)[2] + numberOfAdjacentEnemyCountriesOneUnit;
			}
		}
		
		
		//3. Determine what the difference in units, between the countryTO and its adjacent enemy countries, would be after 
		//   fortifying said countryTo. If the total number of units after fortifying - the enemy's units is => 2, add +1 weight.  
		for (int i = 0; i < possibleMovements.size(); i++) {
			ArrayList<Integer> enemyCountriesAdjacent = findAdjacentEnemyCountries(possibleMovements.get(i)[1]);
			int numberOfUnitsToMove = board.getNumUnits(possibleMovements.get(i)[0])-1; //get the number of units you can move from countryFROM 
			int numberOfUnitsCountryTO = board.getNumUnits(possibleMovements.get(i)[1]);
			int totalUnitsAfterFortifying = numberOfUnitsToMove + numberOfUnitsCountryTO;
			
			for (int adjacentEnemyCountry : enemyCountriesAdjacent) {
				//add weight if moving the units would give you an advantage of at least two units
				if ((totalUnitsAfterFortifying - board.getNumUnits(adjacentEnemyCountry)) >= 2) {
					possibleMovements.get(i)[2]++;
				}
			}
		}
		
		//SORT ALL THE POSSIBLE MOVEMENTS BY WEIGHT. CHOOSE THE OPTION WITH THE HIGHEST WEIGHT
		
		//sort the arrayList possibleMovements according to the value found at index 2 (i.e. the weight of the choice)
		Collections.sort(possibleMovements, new Comparator<int[]>() {
		    public int compare(int[] a, int[] b) {
		        return  Integer.compare(a[2], b[2]); 
		    }
		});
		
		command = translateToStringFortify(possibleMovements.get(0));
		return(command);
	}
	
	
	
	/* Method translates the command choice for the getFortify method to a String to be returned by the bot
	 * 
	 * @param int [] choice: int array containing the countryTo and the countryFrom IDs
	 * @return a string command in the form of "countryFrom countryTo numberOfUnitsToMove"
	 */
	private String translateToStringFortify(int [] choice) {
		String command; 
		
		if (choice[2] == 0) {
			return command = "skip";
		}
		
		String countryFrom = getCountryName(choice[0]);
		String countryTo = getCountryName(choice[1]);
		int numberOfUnitsToMove = board.getNumUnits(choice[0])-1;
		
		command = countryFrom + " " + countryTo + " " + numberOfUnitsToMove;
		return command; 
	}
	
	/* Utility method that returns the name of a Country if given the countryID of said Country
     * @param name: the index of the Country
     */
    public String getCountryName(int countryID) {
    	return GameData.COUNTRY_NAMES[countryID];
    }
    
	/* Private helper method that takes a country id and returns the adjacent countries controlled by the enemy
	 * 
	 * @param countryID: a country id
	 * @return the countries adjacent to the countryID belonging to the enemy
	 */
	private ArrayList<Integer> findAdjacentEnemyCountries(int countryID) {
		ArrayList<Integer> countriesEnemy = findEnemyCountries();
		ArrayList<Integer> countriesEnemyAdjacent = new ArrayList<Integer>();
		
		for (int countryEnemy : countriesEnemy) {
			if (board.isAdjacent(countryID, countryEnemy)) {
				countriesEnemyAdjacent.add(countryEnemy);
			}
		}
		return countriesEnemyAdjacent;
	}
	
	/* Private helper method that takes a country id and returns the adjacent countries controlled by the enemy with one unit
	 * 
	 * @param countryID: a country id
	 * @return the countries adjacent to the countryID belonging to the enemy with one unit
	 */
	private ArrayList<Integer> findEnemyCountriesAdjacentOneUnit(int countryID) {
		ArrayList<Integer> countriesEnemyOneUnit = findEnemyCountries();
		ArrayList<Integer> countriesEnemyAdjacentOneUnit = new ArrayList<Integer>();
		
		for (int countryEnemy : countriesEnemyOneUnit) {
			if (board.isAdjacent(countryID, countryEnemy) && (board.getNumUnits(countryEnemy) == 1)) {
				countriesEnemyAdjacentOneUnit.add(countryEnemy);
			}
		}
		return countriesEnemyAdjacentOneUnit;
	}
	
	/* Private helper method that returns an arrayList of the countries controlled by the enemy
	 */
	private ArrayList<Integer> findEnemyCountries() {
		ArrayList<Integer> countriesEnemyOneUnit = new ArrayList<Integer>();
		for (int id : GameData.CONTINENT_IDS) {
			if ((board.getOccupier(id) == otherPlayerId)) {
				countriesEnemyOneUnit.add(id);
			}
		}
		return countriesEnemyOneUnit;
	}
	
	/* private helper method that returns the borders of a given continent
	 * 
	 * @param continentID = the id of the continent 
	 * @return arrayList of borders 
	 */
	private ArrayList<Integer> findContinentBorder(int continentID) {
		ArrayList<Integer> bordersIds = new ArrayList<Integer>(); 
		switch (continentID) {
		case 0: //North America
			bordersIds.add(8); //Alaska
			bordersIds.add(4); //Greenland
			bordersIds.add(7); //Central America
			break; 
		case 1: //Europe
			bordersIds.add(14); //Iceland
			bordersIds.add(12); //Ukraine
			bordersIds.add(11); //South Europe
			bordersIds.add(10); //W Europe
			break; 
		case 2: //Asia
			bordersIds.add(20); //Ural
			bordersIds.add(16); //Afghanistan
			bordersIds.add(18); //Middle East
			bordersIds.add(23); //Siam
			bordersIds.add(22); //Kamchatka
			break; 
		case 3: //Australia
			bordersIds.add(31); //Indonesia
			break; 
		case 4: //S America
			bordersIds.add(32); //Venezuela
			bordersIds.add(34); //Brazil
			break; 
		case 5: //Africa
			bordersIds.add(37); //N Africa
			bordersIds.add(39); //Egypt
			bordersIds.add(40); //E Africa
			break; 
		}
		return bordersIds; 
	}

	/* private helper method which returns the id of a continent occupied by the bot. It returns 
	 * -1 if the bot does not occupy any continents.
	 */
	private int continentOwned() {
		boolean allOccupied;
		int[] countryIdsContienent;
		for (int i=0; i<GameData.NUM_CONTINENTS; i++) {
			countryIdsContienent = GameData.CONTINENT_COUNTRIES[i];
			allOccupied = true;
			for (int j=0; (j<countryIdsContienent.length) && (allOccupied); j++) {
				if (board.getOccupier(countryIdsContienent[j]) != botId) {
					allOccupied = false;
				}
			}
			if (allOccupied) {
				return GameData.CONTINENT_IDS[i]; //return the id of the continent occupied			
			}
		} 
		return -1; //no continent found
	}
}


