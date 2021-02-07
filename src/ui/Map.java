package ui;

import gamecomponents.*;
import player.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;

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
    private static final int[][] COUNTRY_COORD = {{213, 130}, {265, 145}, {146, 82}, {146, 130}, {310, 60}, {210, 220}, {153, 185}, {155, 248}, {75, 90}, {352, 175}, {365, 265},
            {427, 254}, {505, 160}, {415, 207}, {373, 120}, {443, 103}, {567, 212}, {610, 300}, {514, 290}, {770, 195}, {584, 150}, {705, 70}, {770, 80}, {678, 320}, {686, 140}, {620, 90}, {687, 190}, {665, 250},
            {785, 465}, {755, 378}, {700, 473}, {675, 400}, {208, 310}, {225, 387}, {283, 365}, {235, 445}, {458, 410}, {400, 340}, {463, 480}, {459, 317}, {505, 380}, {528, 473}};
    public static final int[] CONTINENT_IDS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
    private static final int MAP_WIDTH = 640;
    private static final int MAP_HEIGHT = 480;
    private static final int DIAMETER = 20;
    private static final int RADIUS = DIAMETER / 2;
    private static final int NUM_COUNTRIES = COUNTRY_COORD.length;
    private static final int SPACING = 5;
    private BufferedImage image;

    /**
     * Constructor for the Map class. The Constructor takes no argument and simply initialises the array list of Countries.
     */
    public Map() {
        countries = new ArrayList<Country>();
        try {
            image = ImageIO.read(getClass().getResource("/images/map_color.png"));
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    /**
     * Constructor for the Map class. The Constructor takes an instance of Window as an argument and simply initialises the array list of Countries.
     */
    public Map(Window window) {
        countries = new ArrayList<Country>();
        try {
            image = ImageIO.read(new File("src/images/map_color.png"));
        } catch (IOException ex) {
            ex.getMessage();
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

        g.drawImage(image, 0, 0, null);

        drawSeaLines(g);

        if (Window.instruction <= 2) {
        	//draw the country names
            drawCountriesNames(g);
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
        g.drawLine(countries.get(Country.getIndex("Kamchatka")).getCoord_x(), countries.get(Country.getIndex("Kamchatka")).getCoord_y(), countries.get(Country.getIndex("Kamchatka")).getCoord_x()+50, countries.get(Country.getIndex("Kamchatka")).getCoord_y());
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
     * This method draws a Country node at the specified coordinates.
     */
    private void drawCountryNodesStart(Graphics g) {
        for (Country c : countries) {
            //draw the graph nodes to indicate the countries changing color according to the continent they belong to.
            if (c.getContinent() == 0) {
                g.setColor(new Color(105, 150, 111)); //green
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 1) {
                g.setColor(new Color(153, 79, 67)); //red 
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 2) {
                g.setColor(new Color(108, 224, 221)); //cyan
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 3) {
                g.setColor(new Color(163, 120, 28)); //orange
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 4) {
                g.setColor(new Color(213, 167, 219)); //pink
                g.fillOval(c.getCoord_x() - RADIUS, c.getCoord_y() - RADIUS, DIAMETER, DIAMETER);
            } else if (c.getContinent() == 5) {
                g.setColor(new Color(66, 106, 214)); //blue
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
        	String name = c.getName();
        	Color textColor = new Color(75, 102, 102);
        	int x = c.getCoord_x() - DIAMETER;
        	int y =  c.getCoord_y() + DIAMETER;

            g.setColor(textColor);
            g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
            g.drawString(name, x, y);
        }
    }
}
