# BotHarmon

##**Risk Board Game Implementation using Java Swing**

@Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)

###Introduction
Please note, *this project is still in process*
This risk implementation allows for a two player risk game with four passive neutrals and/or a one player risk game against an AI with four passive neutrals. 

To build in intelj, left click and run the file in src/com.botharmon/Main.java
To build in Eclipse, right click the file in src/com.botharmon/Main.java -> run as -> Java Application

The .jar file can be found in out/artifacts/BotHarmon_jar/BotHarmon.jar

###Project design 
#####Package Com.botHarmon
* **Game.java** - Point of access of the program. Initialises the main components for the game including the Map, Player, Deck and Window (ui) objects. 
Additional components may be added later.

#####Package gamecomponents
* **Country.java** - used to construct a Country object (the territories that belong to each player in the game). Each country also has a country coordinates variable used in the Map.java file to draw the different ui components for each country. 

* **Card.java** - used to construct Card objects with countryName and unitType (Infantry, Cavalry, Artillery) instance variables. 

* **Deck.java** creates the Deck object which stores an ArrayList of cards. 

#####Package player
* **Player.java** - abstract superclass for the players in the game.

* **ActivePlayer.java** (for Players 1 and 2) and

* **PassivePlayer.java** (for neutral players) extend this superclass. Each players has a name, colour, an ArrayList of cards and an ArrayList of countries controlled. An array of ActivePlayer and PassivePlayers  objects are created in Game.java which are instances of the abstract Player.java class
 
* **Dice.java** rolls a randomised dice in order to check which player is to start allocating units and attacking.

* **Turns.java** The stages of the game are implemented here. The game has five stages: enter_names, assign_countries, roll_to_place_territories, main_turn, end. User input validation can also be found here. 

#####Package library
* **CustomArrayList.java** A customArrayList that uses a HasMap in order to make the country information easily accessible: the key is the country name. 

* **TextParser.java** An array containing all of the patterns for the game to recognise, and the Names of the countries the refer to. It contains the single method, TextParser.parse(), which takes the user's shortened version of the name, and returns the full country name to be used by the CustomArrayList as key.

#####Package ui
* **Window.java** - The main UI JFrame. Creates the various panels, images and textarea used for the game's UI.

* **Map.java** JPanel that display the risk map. Stores an ArrayList of countries, as well as key data used to Graphic the MapPanel in the UI.

* **DisplayText.java** Displays game instruction to the user, as well as error messages when the user enters erroneous input.

* **CommandPanel.java** JPanel containing a textArea where the user can enter commands. 

* **PlayerInfo** JPanel that displays a legend of the players and their associated colours. 

#####Package Tests
* Contains the JUnit testing for all the testable (i.e. non ui) classes. 
