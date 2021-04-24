import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;


public class BotHarmon implements Bot {

    private BoardAPI board;
    private PlayerAPI player;
    private int botId;
    private int otherPlayerId;
    private int countriesInvaded;


    BotHarmon(BoardAPI inBoard, PlayerAPI inPlayer) {
        board = inBoard;
        player = inPlayer;
        botId = player.getId();
        otherPlayerId = 0;
        countriesInvaded = 0;
        return;
    }

    public String getName() {
        String command = "";
        command = "BotHarmon";
        return (command);
    }

    // used for allocation at the beginning of the game and at the start of each turn
    public String getReinforcement() {
        // 1. find countries controlled by bot and countries controlled by other players
        ArrayList<Integer> botCountryIDs = new ArrayList<Integer>();
        ArrayList<Integer> otherPlayersCountryIDs = new ArrayList<Integer>();

        for (int i = 0; i < GameData.NUM_COUNTRIES; i++) {
            if (board.getOccupier(i) == botId)
                botCountryIDs.add(i);
            else
                otherPlayersCountryIDs.add(i);
        }

        /* 2. Create a list of weights for each country, the first element being its id, the second being its weight
         * 3. Add the number of surrounding units to the weight
         * 4. Add the number of activePlayers occupying
         */
        ArrayList<int[]> countryWeights = new ArrayList<>(botCountryIDs.size());

        for (Integer botCountryID: botCountryIDs) { // iterate through each botCountryID's adjacent country
            int numberOfSurroundingUnits = 0;
            int activePlayersOccupied = 0;
            for (Integer otherPlayersCountryID: otherPlayersCountryIDs) {
                if(board.isAdjacent(botCountryID, otherPlayersCountryID)) {
                    numberOfSurroundingUnits++;
                    if(board.getOccupier(otherPlayersCountryID) == otherPlayerId)
                        activePlayersOccupied++;
                }
            }
            int[] countryIDAndWeight = {botCountryID, numberOfSurroundingUnits + activePlayersOccupied};
            countryWeights.add(countryIDAndWeight);
        }
        /* 5. create a list of continents owned by a bot
         */
        ArrayList<Integer> botContinents = new ArrayList<>();

        for (int i = 0; i < GameData.NUM_CONTINENTS; i++) {
            int [] countryIdsContinent = GameData.CONTINENT_COUNTRIES[i];
            boolean allOccupied = true;
            for (int j = 0; (j < countryIdsContinent.length) && (allOccupied); j++) {
                if (board.getOccupier(countryIdsContinent[j]) != botId) {
                    allOccupied = false;
                }
            }
            if(allOccupied)
                botContinents.add(i);
        }

        /* 6. use this botContinents list to check if each country is part of a front line you need to defend
         * 7. Subtract 1 if the country is part of part of the front line (subtracting means the country will
         * have a more favourable weight)
         */
        for (Integer continent: botContinents) {
            for(Integer countryFrontLine: findContinentBorder(continent)) {
                for(int[] weight: countryWeights) {
                    if(weight[0] == countryFrontLine) {
                        weight[1]--;
                        break;
                    }
                }
            }
        }
        // 8. sort the weights in ascending order
        countryWeights.sort(Comparator.comparingInt(a -> a[1]));

        // return the first country weight in the list
        return getCountryName(countryWeights.get(0)[0]) + " " + player.getNumUnits();
    }

    // allocating countries passive players at the start of the game
    public String getPlacement(int forPlayer) {
        ArrayList<int []> forPlayerCountries = new ArrayList<>();
        for(int i = 0; i < GameData.NUM_COUNTRIES; i++) {
            if(board.getOccupier(i) == forPlayer) {
                forPlayerCountries.add(new int[] {i, board.getNumUnits(i)});
            }
        }
        forPlayerCountries.sort(Comparator.comparingInt(a -> a[1]));
//        int[] countryWithLowestUnits = {0, board.getNumUnits(0)};
//        for(int i = 1; i < forPlayerCountries.size(); i++) {
//            if(board.getNumUnits(i) < countryWithLowestUnits[1]) {
//                countryWithLowestUnits[0] = i;
//                countryWithLowestUnits[1] = board.getNumUnits(i);
//            }
//        }
        return getCountryName(forPlayerCountries.get(0)[0]);
    }

    public String getCardExchange() {
        if (player.getCards().size() < 3)
            return "skip";
        int[] exchangeSet = new int[3];
        char[] insignias = {'i', 'c', 'a'};
        for (int i = 0; i < Deck.SETS.length; i++) {
            if (player.isCardsAvailable(Deck.SETS[i])) {
                exchangeSet = Deck.SETS[i];
                break;
            }
        }
        return "" + insignias[exchangeSet[0]] + insignias[exchangeSet[1]] + insignias[exchangeSet[2]];
    }

    public String getBattle() {
        String command = "";
        /* 1. find all possible territories to attack []
         * for each of those territories, assign points 			* according to:
         * 1. can I break up a continent?
         * 2. can I attack the the activePlayer?
         * 3. Did I get a card already?
         * --> choose to attack territory w/ highest points
         * check tot number of troops you vs your opponent to
         * to determine whether to attack.
         */

        if (board.isInvasionSuccess()) {
            countriesInvaded++;
        }

        //Step 1. find all the countries the bot controls with more than one unit
        ArrayList<Integer> countriesControlledMoreThanUnit = findCountriesWithMoreThanOneUnit();

        //if none of the countries controlled have more than one unit, cannot attack - return skip
        if (countriesControlledMoreThanUnit.size() == 0) {
            return command = "skip";
        }

        //Step 2. Find all the countries that are connected to those in step 1 and create a collection of all the possible attacks
        ArrayList<int[]> possibleAttacks = new ArrayList<int[]>();
        for (int controlled : countriesControlledMoreThanUnit) {
            for (int i = 0; i < GameData.COUNTRY_NAMES.length; i++) {
                if (board.isAdjacent(controlled, i) && board.getOccupier(i) != botId) {
                    int[] countryAttackingToAttack = new int[3];
                    countryAttackingToAttack[0] = controlled; //country attacking
                    countryAttackingToAttack[1] = i;          //country to attack
                    //[2] is left empty. The weight of the choice is added here.
                    possibleAttacks.add(countryAttackingToAttack);
                }
            }
        }

        //* 2. can I attack the the activePlayer?



        //if no attacks are possible, cannot attack - return skip
        if (possibleAttacks.size() == 0) {
            return command = "skip";
        }

        // Step 3. Add weight to attack a country to break a continent checking if the border is possible to attack
        int continentId = continentOwned(otherPlayerId);
        if (continentId != -1){
            ArrayList<Integer> countriesOnTheBorder = findContinentBorder(continentId);
            for (int i = 0; i < possibleAttacks.size(); i++) {
                for (int j = 0; j < countriesOnTheBorder.size(); j++){
                    if (possibleAttacks.get(i)[1] == countriesOnTheBorder.get(j)){
                        possibleAttacks.get(i)[2] = possibleAttacks.get(i)[2] + 2;
                    }
                }
            }
        }

        //Step 4. Determine if any of the countries adjacent to the countryTo belong to the other player and if these countries
        //   only have one unit (easy to defeat). For each of the adjacent enemy countries with one unit, add +1 weight.
        for (int i = 0; i < possibleAttacks.size(); i++) {
            ArrayList<Integer> enemyCountriesAdjacentOneUnit = findEnemyCountriesAdjacentOneUnit(possibleAttacks.get(i)[1]);

            if (enemyCountriesAdjacentOneUnit.size() > 0) {
                int numberOfAdjacentEnemyCountriesOneUnit = enemyCountriesAdjacentOneUnit.size();
                //add one to the weight of the possible movement for each of the adjacent enemy countries w/ one unit
                possibleAttacks.get(i)[2] = possibleAttacks.get(i)[2] + numberOfAdjacentEnemyCountriesOneUnit;
            }
        }

        //Step 5. Determine if any of the countries adjacent to the countryTo belong to neutral players and if these countries
        //   only have one unit (easy to defeat). For each of the adjacent enemy countries with one unit, add +1 weight.
        for (int i = 0; i < possibleAttacks.size(); i++) {
            ArrayList<Integer> enemyCountriesAdjacentOneUnit = findEnemyCountriesAdjacentOneUnit(possibleAttacks.get(i)[1]);

            if (enemyCountriesAdjacentOneUnit.size() > 0) {
                int numberOfAdjacentEnemyCountriesOneUnit = enemyCountriesAdjacentOneUnit.size();
                //add one to the weight of the possible movement for each of the adjacent enemy countries w/ one unit
                possibleAttacks.get(i)[2]++;
            }
        }

        //Step 6. Check if the bot won at least more than 3 countries
        if (countriesInvaded > 3){
            countriesInvaded = 0;
            return command = "skip";
        }

        //SORT ALL THE POSSIBLE ATTACKS BY WEIGHT. CHOOSE THE OPTION WITH THE HIGHEST WEIGHT

        //sort the arrayList possibleAttacks according to the value found at index 2 (i.e. the weight of the choice)
        Collections.sort(possibleAttacks, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[2], b[2]);
            }
        });

        command = translateToStringBattle(possibleAttacks.get(0));
        return (command);
    }

    public String getDefence(int countryId) {
        String command = "";
        int unitsDefence = board.getNumUnits(countryId);

        if (unitsDefence >= 2){
            unitsDefence = 2;
        } else {
            unitsDefence = 1;
        }

        command = String.valueOf(unitsDefence);
        return (command);
    }

    public String getMoveIn(int attackCountryId) {
        String command = "";
        int unitsMoving = board.getNumUnits(attackCountryId) - 1;

        if (unitsMoving > 4){
            unitsMoving = unitsMoving - 2;
        }

        command = String.valueOf(unitsMoving);
        return (command);
    }

    public String getFortify() {
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
        ArrayList<Integer> countriesControlledMoreThanUnit = findCountriesWithMoreThanOneUnit();

        //if none of the countries controlled have more than one unit, cannot fortify - return skip
        if (countriesControlledMoreThanUnit.size() == 0) {
            return command = "skip";
        }

        //Step 2. Find all the countries that are connected to those in step 1 and create a collection of all the possible fortification movements
        ArrayList<int[]> possibleMovements = new ArrayList<int[]>();
        for (int controlled : countriesControlledMoreThanUnit) {
            for (int i = 0; i < GameData.COUNTRY_NAMES.length; i++) {
                if (board.isConnected(controlled, i)) {
                    int[] countryFromCountryTo = new int[3];
                    countryFromCountryTo[0] = controlled; //country from
                    countryFromCountryTo[1] = i;          //country to
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
        int continentOwnedID = continentOwned(botId);
        if (continentOwnedID != -1) {
            ArrayList<Integer> continentBorders = findContinentBorder(continentOwnedID);

            //find out whether it is possible to fortify a border. If it is, add +1 weight to that possible movement.
            for (int i = 0; i < possibleMovements.size(); i++) {
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
            int numberOfUnitsToMove = board.getNumUnits(possibleMovements.get(i)[0]) - 1; //get the number of units you can move from countryFROM
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
                return Integer.compare(a[2], b[2]);
            }
        });

        command = translateToStringFortify(possibleMovements.get(0));
        return (command);
    }

    /* Private helper method that returns an arrayList of the countries with more than one unit
     */
    private ArrayList<Integer> findCountriesWithMoreThanOneUnit() {
        ArrayList<Integer> countriesControlledMoreThanUnit = new ArrayList<Integer>();
        for (int id : GameData.CONTINENT_IDS) {
            if ((board.getOccupier(id) == botId) && (board.getNumUnits(id) > 1)) {
                countriesControlledMoreThanUnit.add(id);
            }
        }
        return countriesControlledMoreThanUnit;
    }

    /* Method translates the command choice for the getFortify method to a String to be returned by the bot
     *
     * @param int [] choice: int array containing the countryTo and the countryFrom IDs
     * @return a string command in the form of "countryFrom countryTo numberOfUnitsToMove"
     */
    private String translateToStringFortify(int[] choice) {
        String command;

        if (choice[2] == 0) {
            return command = "skip";
        }

        String countryFrom = getCountryName(choice[0]);
        String countryTo = getCountryName(choice[1]);
        int numberOfUnitsToMove = board.getNumUnits(choice[0]) - 1;

        command = countryFrom + " " + countryTo + " " + numberOfUnitsToMove;
        return command;
    }

    /* Method translates the command choice for the getBattle method to a String to be returned by the bot
     *
     * @param int [] choice: int array containing the countryAttacking and the countryToAttack IDs
     * @return a string command in the form of "countryAttacking countryToAttack numberOfUnitsToAttack"
     */
    private String translateToStringBattle(int[] choice) {
        String command;

        if (choice[2] == 0) {
            return command = "skip";
        }

        String countryAttacking = getCountryName(choice[0]);
        String countryToAttack = getCountryName(choice[1]);
        int numberOfUnitsToAttack = board.getNumUnits(choice[0]);
        if (numberOfUnitsToAttack > 3){
            numberOfUnitsToAttack = 3;
        } else if (numberOfUnitsToAttack == 3){
            numberOfUnitsToAttack = 2;
        } else {
            numberOfUnitsToAttack = 1;
        }

        command = countryAttacking + " " + countryToAttack + " " + numberOfUnitsToAttack;
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
        ArrayList<Integer> countriesEnemy = findEnemyCountries(otherPlayerId);
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
        ArrayList<Integer> countriesEnemyOneUnit = findEnemyCountries(otherPlayerId);
        ArrayList<Integer> countriesEnemyAdjacentOneUnit = new ArrayList<Integer>();

        for (int countryEnemy : countriesEnemyOneUnit) {
            if (board.isAdjacent(countryID, countryEnemy) && (board.getNumUnits(countryEnemy) == 1)) {
                countriesEnemyAdjacentOneUnit.add(countryEnemy);
            }
        }
        return countriesEnemyAdjacentOneUnit;
    }

    /* Private helper method that takes a country id and returns the adjacent countries controlled by the neutral with one unit
     *
     * @param countryID: a country id
     * @return the countries adjacent to the countryID belonging to the neutral with one unit
     */
    private ArrayList<Integer> findNeutralCountriesAdjacentOneUnit(int countryID) {
        ArrayList<Integer> countriesNeutralOneUnit = new ArrayList<>();
        countriesNeutralOneUnit.addAll(findEnemyCountries(2));
        countriesNeutralOneUnit.addAll(findEnemyCountries(3));
        countriesNeutralOneUnit.addAll(findEnemyCountries(4));
        countriesNeutralOneUnit.addAll(findEnemyCountries(5));

        ArrayList<Integer> countriesNeutralAdjacentOneUnit = new ArrayList<Integer>();

        for (int countryNeutral : countriesNeutralOneUnit) {
            if (board.isAdjacent(countryID, countryNeutral) && (board.getNumUnits(countryNeutral) == 1)) {
                countriesNeutralAdjacentOneUnit.add(countryNeutral);
            }
        }
        return countriesNeutralAdjacentOneUnit;
    }

    /* Private helper method that returns an arrayList of the countries controlled by the enemy
     */
    private ArrayList<Integer> findEnemyCountries(int id) {
        ArrayList<Integer> countriesEnemyOneUnit = new ArrayList<Integer>();
        for (int i = 0; i < GameData.COUNTRY_NAMES.length; i++) {
            if ((board.getOccupier(i) == id)) {
                countriesEnemyOneUnit.add(i);
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
    private int continentOwned(int id) {
        boolean allOccupied;
        int[] countryIdsContinent;
        for (int i = 0; i < GameData.NUM_CONTINENTS; i++) {
            countryIdsContinent = GameData.CONTINENT_COUNTRIES[i];
            allOccupied = true;
            for (int j = 0; (j < countryIdsContinent.length) && (allOccupied); j++) {
                if (board.getOccupier(countryIdsContinent[j]) != id) {
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


