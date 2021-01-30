package risk;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

class Map extends JPanel {
	
	/**
	 * Instance variables 
	 */
	protected ArrayList<Country> countries;
	public static final String[] COUNTRY_NAMES = {
			"Ontario","Quebec","NW Territory","Alberta","Greenland","E United States","W United States","Central America","Alaska",
			"Great Britain","W Europe","S Europe","Ukraine","N Europe","Iceland","Scandinavia",
			"Afghanistan","India","Middle East","Japan","Ural","Yakutsk","Kamchatka","Siam","Irkutsk","Siberia","Mongolia","China",
			"E Australia","New Guinea","W Australia","Indonesia",
			"Venezuela","Peru","Brazil","Argentina",
			"Congo","N Africa","S Africa","Egypt","E Africa","Madagascar"};
	public static final int[][] ADJACENT = {{4,1,5,6,3,2},{4,5,0},{4,0,3,8},{2,0,6,8},{14,1,0,2},{0,1,7,6},{3,0,5,7},{6,5,32},{2,3,22},{14,15,13,10},    
			{9,13,11,37}, {13,12,18,39,10},{20,16,18,11,13,15},{15,12,11,10,9},{15,9,4},{12,13,14},{20,27,17,18,12}, {16,27,23,18},
			{12,16,17,40,39,11},{26,22},{25,27,16,12},{22,24,25},{8,19,26,24,21},{27,31,17},{21,22,26,25},{21,24,26,27,20},{24,22,19,27,25},{26,23,17,16,20,25},
			{29,30},{28,30,31},{29,28,31},{23,29,30},{7,34,33},{32,34,35},{32,37,35,33},{33,34},{37,40,38},{10,11,39,40,36,34},{36,40,41},{11,18,40,37},
			{39,18,41,38,36,37},{38,40}};
	private static final int[][] COUNTRY_COORD = {{191,150},{255,161},{146,86},{123,144},{314,61},{205,235},{135,219},{140,299},{45,89},{370,199},{398,280},    
			{465,270},{547,180},{460,200},{393,127},{463,122},{628,227},{679,332},{572,338},{861,213},{645,152},{763,70},{827,94},{751,360},{750,140},{695,108},{760,216},{735,277},{889,537},
			{850,429},{813,526},{771,454},{213,352},{221,426},{289,415},{233,523},{496,462},{440,393},{510,532},{499,354},{547,432},{586,545}};	
	public static final int[] CONTINENT_IDS = {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,5,5};
	private static final int MAP_WIDTH = 1000;
   	private static final int MAP_HEIGHT = 600;
    	private static final int DIAMETER = 30; 
   	 private static final int RADIUS = DIAMETER/2; 
   	 private static final int NUM_COUNTRIES = COUNTRY_COORD.length; 
    
    /** 
    * Constructor for the Map class. The Constructor takes no argument and simply initialises the array list of Countries. 
    */
    public Map() {
    	countries = new ArrayList<Country>();
    }
    
    /*
     * This method creates a new instance of the class Country for each of the countries - assigning it its own: 
     * 1) name, 2) X coordinates, 3) Y coordinates, 4) continent it belongs to, 5) arrayList of adjacent countries indexes.
     */
    public void initialiseCountries() {
		int j = 0; 
		for (int i = 0; i<NUM_COUNTRIES; i++) {
			countries.add(new Country(COUNTRY_NAMES[i], COUNTRY_COORD[i][j], COUNTRY_COORD[i][j+1])); //add Countries instance variables to the arrayList of type Country
			countries.get(i).setContinent(CONTINENT_IDS[i]); //set the Continent id for each country
			
			ArrayList<Integer> adjCountries = new ArrayList<Integer>();  //Create an array to contain the indexes of the countries adjacent to Country.get(i)
			for (int k = 0; k < ADJACENT[i].length; k++) {
				adjCountries.add(ADJACENT[i][k]);
			}
			countries.get(i).setAdjCountries(adjCountries); 
			System.out.println(countries.get(i).toString()); //for testing purposes
		}
	}
    
    /**
    * Overrides the getPreferredSize() method of JPanel. It sets the Panel dimensions to MAP_HEIGHT and MAP_WIDTH.
    */
    @Override
    public Dimension getPreferredSize () {
        return new Dimension (MAP_WIDTH, MAP_HEIGHT);
    }
    
    /** Overrides the paintComponent method of JPanel. The method draws a filled circle for each Country at the coordinates(x-radius, y-radius). The color of the country
    * depends on the Continent it belongs to. Lastly, lines between adjacent Countries are drawn. 
    */
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	for (Country c : countries) {
    		if (c.getContinent() == 0) {
    			g.setColor(Color.green);
    			g.fillOval(c.getCoord_x()-RADIUS, c.getCoord_y()-RADIUS, DIAMETER, DIAMETER);
    		}
    		else if (c.getContinent() == 1) {
    			g.setColor(Color.red);
    			g.fillOval(c.getCoord_x()-RADIUS, c.getCoord_y()-RADIUS, DIAMETER, DIAMETER);
    		}
    		else if (c.getContinent() == 2) {
    			g.setColor(Color.cyan);
    			g.fillOval(c.getCoord_x()-RADIUS, c.getCoord_y()-RADIUS, DIAMETER, DIAMETER);
    		}
    		else if (c.getContinent() == 3) {
    			g.setColor(Color.orange);
    			g.fillOval(c.getCoord_x()-RADIUS, c.getCoord_y()-RADIUS, DIAMETER, DIAMETER);
    		}
    		else if (c.getContinent() == 4) {
    			g.setColor(Color.pink);
    			g.fillOval(c.getCoord_x()-RADIUS, c.getCoord_y()-RADIUS, DIAMETER, DIAMETER);
    		}
    		else if (c.getContinent() == 5) {
    			g.setColor(Color.blue);
    			g.fillOval(c.getCoord_x()-RADIUS, c.getCoord_y()-RADIUS, DIAMETER, DIAMETER);
    		}
    		
    		for (int i = 0; i<c.getAdjCountriesLength(); i++) {
    			//g.setColor(Color.black);
        		g.drawLine(c.getCoord_x(), c.getCoord_y(), countries.get(c.getAdjCountries().get(i)).getCoord_x(), countries.get(c.getAdjCountries().get(i)).getCoord_y());
        	}
    	}
    	
    	
    }
}

