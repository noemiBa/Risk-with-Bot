# BotHarmon

29/01/2021 (Noemi)
Three classes added: 
- Window.java: controls the main JFrame and the UI components. This is the point of entry of the program.
- Map.java: this class controls the Map JPanel. It extends the class JPanel and overrides its paintComponent and getPreferredDimension methods. In this class, instances of the class Country for each of the Territories are created and kept in an ArrayList called countries. 
- Country.java: a basic class that defines the Object Country. Each Country has: 1) name, 2) X coordinates, 3) Y coordinates, 4) continent it belongs to, 5) arrayList of adjacent countries indexes. I have also added some methods that I believe may come in handy in the future, even though they have not been used yet. 

Note: the code needs commenting. I will comment it tomorrow. 
