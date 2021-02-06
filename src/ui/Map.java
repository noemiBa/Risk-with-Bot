package ui;

import gamecomponents.*;
import player.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Map extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Instance variables
     */
    private ArrayList<Country> countries;
    public static final String[] COUNTRY_NAMES = {
            "Ontario", "Quebec", "NW Territory", "Alberta", "Greenland", "E United States", "W United States", "Central America", "Alaska",
            "Great Britain", "W Europe", "S Europe", "Ukraine", "N Europe", "Iceland", "Scandinavia",
            "Afghanistan", "India", "Middle East", "Japan", "Ural", "Yakutsk", "Kamchatka", "Siam", "Irkutsk", "Siberia", "Mongolia", "China",
            "E Australia", "New Guinea", "W Australia", "Indonesia", "Venezuela", "Peru", "Brazil", "Argentina",
            "Congo", "N Africa", "S Africa", "Egypt", "E Africa", "Madagascar"};
    public static final int[][] ADJACENT = {{4, 1, 5, 6, 3, 2}, {4, 5, 0}, {4, 0, 3, 8}, {2, 0, 6, 8}, {14, 1, 0, 2}, {0, 1, 7, 6}, {3, 0, 5, 7}, {6, 5, 32}, {2, 3, 22}, {14, 15, 13, 10},
            {9, 13, 11, 37}, {13, 12, 18, 39, 10}, {20, 16, 18, 11, 13, 15}, {15, 12, 11, 10, 9}, {15, 9, 4}, {12, 13, 14}, {20, 27, 17, 18, 12}, {16, 27, 23, 18},
            {12, 16, 17, 40, 39, 11}, {26, 22}, {25, 27, 16, 12}, {22, 24, 25}, {8, 19, 26, 24, 21}, {27, 31, 17}, {21, 22, 26, 25}, {21, 24, 26, 27, 20}, {24, 22, 19, 27, 25}, {26, 23, 17, 16, 20, 25},
            {29, 30}, {28, 30, 31}, {29, 28, 31}, {23, 29, 30}, {7, 34, 33}, {32, 34, 35}, {32, 37, 35, 33}, {33, 34}, {37, 40, 38}, {10, 11, 39, 40, 36, 34}, {36, 40, 41}, {11, 18, 40, 37},
            {39, 18, 41, 38, 36, 37}, {38, 40}};
    private static final int[][] COUNTRY_COORD = {{213, 170}, {265, 185}, {146, 122}, {146, 170}, {310, 100}, {210, 260}, {153, 225}, {155, 288}, {75, 130}, {352, 215}, {365, 305},
            {427, 294}, {505, 200}, {415, 247}, {373, 160}, {443, 143}, {567, 252}, {610, 340}, {514, 330}, {770, 235}, {584, 190}, {705, 110}, {770, 120}, {678, 360}, {686, 180}, {620, 130}, {687, 230}, {665, 290},
            {785, 505}, {755, 418}, {700, 513}, {675, 440}, {208, 350}, {225, 427}, {283, 405}, {235, 485}, {458, 450}, {400, 380}, {463, 520}, {459, 357}, {505, 420}, {528, 513}};
    public static final int[] CONTINENT_IDS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
    private static final int MAP_WIDTH = 640;
    private static final int MAP_HEIGHT = 480;
    private static final int DIAMETER = 20;
    private static final int RADIUS = DIAMETER / 2;
    private static final int NUM_COUNTRIES = COUNTRY_COORD.length;
    private static final int SPACING = 5;
    private BufferedImage image;
    private static int i;

    /**
     * Constructor for the Map class. The Constructor takes no argument and simply initialises the array list of Countries.
     */
    public Map() {
        countries = new ArrayList<Country>();
        try {
            image = ImageIO.read(new File("src/images/map2.png"));
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public Map(Window window) {
        countries = new ArrayList<Country>();
        try {
            image = ImageIO.read(new File("src/images/11.png"));
        } catch (IOException ex) {
            // handle exception...
        }

    }

    /*Accessor methods*/
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /*
     * This method creates a new instance of the class Country for each of the countries - assigning it its own:
     * 1) name, 2) X coordinates, 3) Y coordinates, 4) continent it belongs to, 5) arrayList of adjacent countries indexes.
     */
    public void initialiseCountries() {
        int j = 0;
        for (int i = 0; i < NUM_COUNTRIES; i++) {
            countries.add(new Country(COUNTRY_NAMES[i], COUNTRY_COORD[i][j], COUNTRY_COORD[i][j + 1])); //add Countries instance variables to the arrayList of type Country
            countries.get(i).setContinent(CONTINENT_IDS[i]); //set the Continent id for each country

            ArrayList<Integer> adjCountries = new ArrayList<Integer>();  //Create an array to contain the indexes of the countries adjacent to Country.get(i)
            for (int k = 0; k < ADJACENT[i].length; k++) {
                adjCountries.add(ADJACENT[i][k]);
            }
            countries.get(i).setAdjCountries(adjCountries);
        }
    }

    public static String[] getCountryNames() {
        return COUNTRY_NAMES;
    }

    /**
     * Overrides the getPreferredSize() method of JPanel. It sets the Panel dimensions to MAP_HEIGHT and MAP_WIDTH.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MAP_WIDTH, MAP_HEIGHT);
    }


    /**
     * Overrides the paintComponent method of JPanel. The method draws a filled circle for each Country at the coordinates(x-radius, y-radius). The color of the country
     * depends on the Continent it belongs to. Lastly, lines between adjacent Countries are drawn.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 50, null);

        drawSeaLines(g);

        if (Window.instruction <= 2) {
            drawCountryNodesStart(g);
            drawMilitaryUnitsStart(g);
        } else {
            Player[] players = Game.getPlayers();
            drawCountriesNames(g);
            drawCountryNodes(g, players);
            drawMilitaryUnits(g);
        }
    }

    /* Private helper method to the main paintComponent method.
     * This method draws sea lines between adjacent countries.
     */
    private void drawSeaLines(Graphics g) {
        for (Country c : countries) { //draw the sea lines first as they will be in the background.
            for (int i = 0; i < c.getAdjCountriesLength(); i++) {

                //do NOT draw the sea line between Alaska and Kamchatka in the middle of the screen.
                if ((c.getName() != "Alaska" || countries.get(c.getAdjCountries().get(i)).getName() != "Kamchatka") && (c.getName() != "Kamchatka" || countries.get(c.getAdjCountries().get(i)).getName() != "Alaska")) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawLine(c.getCoord_x(), c.getCoord_y(), countries.get(c.getAdjCountries().get(i)).getCoord_x(), countries.get(c.getAdjCountries().get(i)).getCoord_y());
                }
            }
        }

        //draw the sea lines between Alaska and Kamchatka
        g.drawLine(countries.get(Country.getIndex("Alaska")).getCoord_x(), countries.get(Country.getIndex("Alaska")).getCoord_y(), 0, countries.get(Country.getIndex("Alaska")).getCoord_y());
        g.drawLine(countries.get(Country.getIndex("Kamchatka")).getCoord_x(), countries.get(Country.getIndex("Kamchatka")).getCoord_y(), MAP_WIDTH, countries.get(Country.getIndex("Kamchatka")).getCoord_y());
    }

    /* Private helper method to the main paintComponent method.
     * This method draws the number of militaryUnits in the center of each Country node.
     */
    private void drawMilitaryUnitsStart(Graphics g) {
        //Draw the number of military units on the country node.
        for (Country c : countries) {
            g.setColor(Color.black);
            g.drawString(String.valueOf("0"), c.getCoord_x() - SPACING, c.getCoord_y() + SPACING);
        }
    }

    private void drawMilitaryUnits(Graphics g) {
        //Draw the number of military units on the country node.
        for (Country c : countries) {
            g.setColor(Color.black);
            g.drawString(String.valueOf(c.getNumberOfUnits()), c.getCoord_x() - SPACING, c.getCoord_y() + SPACING);
        }
    }

    /* Private helper method to the main paintComponent method.
     * This method draws a Country node at the specified coordinates, as well as the name of said Country.
     */
    private void drawCountryNodesStart(Graphics g) {
        for (Country c : countries) {
            //draw the country names
            drawCountriesNames(g);

            //draw the graph nodes to indicate the countries changing color according to the continent they belong to.
            if (c.getContinent() == 0) {
                g.setColor(Color.green);
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 1) {
                g.setColor(Color.red);
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 2) {
                g.setColor(Color.cyan);
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 3) {
                g.setColor(Color.orange);
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 4) {
                g.setColor(Color.pink);
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 5) {
                g.setColor(Color.blue);
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            }
        }
        this.setBackground(Color.black);
    }

    private void drawCountryNodes(Graphics g, Player[] players) {
        for (Player p : players) {

            for (Country c : p.getCountriesControlled()) {

                //draw the graph nodes to indicate the countries changing color according to the continent they belong to.
                // if (p.getCountriesControlled().contains(c.getName())) {
                g.setColor(p.getPlayerColor());
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
                //  }
            }
        }
    }

    public void drawCountriesNames(Graphics g) {
        for (Country c : countries) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
            g.drawString(c.getName(), c.getCoord_x() - DIAMETER, c.getCoord_y() + DIAMETER);
        }
    }
}
