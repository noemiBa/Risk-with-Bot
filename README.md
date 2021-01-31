# BotHarmon

29/01/2021 (Noemi)
Three classes added: 
- Window.java: controls the main JFrame and the UI components. This is the point of entry of the program.
- Map.java: this class controls the Map JPanel. It extends the class JPanel and overrides its paintComponent and getPreferredDimension methods. In this class, instances of the class Country for each of the Territories are created and kept in an ArrayList called countries. 
- Country.java: a basic class that defines the Object Country. Each Country has: 1) name, 2) X coordinates, 3) Y coordinates, 4) continent it belongs to, 5) arrayList of adjacent countries indexes. I have also added some methods that I believe may come in handy in the future, even though they have not been used yet. 

Note: the code needs commenting. I will comment it tomorrow. 

30/01/2021 (Jess)
7 classes added:
- Folder Hierarchy added and restructured, packages added for different groups of classes 
- Player.java contains a Player interface implemented in PassivePlayer.java and extended in ActivePlayer.java
- Card.java contains a Card class used as the generic type in the cards ArrayList located in Deck.java
- Game.java is currently being used for testing but it is intended to be used for game logic and ui changes throughout the game
- Main.java will run the finished Game class.

31/01/2021 (Noemi)
- Map Panel now displays the country names and number of military units.
- Fixed and closed some issues.
