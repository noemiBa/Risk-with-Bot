# BotHarmon

<b>Risk Board Game Implementation using Java Swing</b>

@Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)

Please note, <i>this project is still in process</i>. 
This risk implementation allows for a two player risk game with four passive neutrals and/or 
a one player risk game against an AI with four passive neutrals. 

To build in intelj, left click and run the file in src/com.company/Main.java

The .jar file can be found in out/artifacts/BotHarmon_jar/BotHarmon.jar

Game.java - creates the main components for the game including the Map, Player, Deck and Window (ui) objects. 
Additional components may be added later. Can be modified to add more or less neutral and active 
players as needed using the newPlayers() method.

Country.java - used as a constructor the Country object (the territories belong to each player in the game). 
Each country also has a country coordinates variable used in the Map.java file to draw the different ui 
components for each country. 

Card.java - used as a constructor for Card objects with countryName and unitType instance variables. 
Deck.java creates the Deck object which stores an ArrayList of cards. 

Player.java - abstract superclass for the players in the game - ActivePlayer.java (for Players 1 and 2) 
and PassivePlayer.java (for neutral players) extend this superclass. Each players has a name, colour, 
an ArrayList of cards and an ArrayList of countries controlled. An array of ActivePlayer and PassivePlayers 
objects are created in Game.java which are instances of the abstract Player.java class 

Map.java - used as a constructor for the Map object. Stores an ArrayList of countries, as well as key data used to 
Graphic components in the UI.

Window.java - used as a constructor for Window objects. creates the various panel, image, textbox and button objects used
for the game's UI.
