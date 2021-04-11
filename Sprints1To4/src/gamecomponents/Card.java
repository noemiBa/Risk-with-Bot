package gamecomponents;

/** Creates a new Card object with the card's countryName and unitType
 * The Deck class uses this card object by creating a deck as an ArrayList of 42 Card objects
 * each with their own countryName and unityType
 * */
public class Card
{
    private char unitType;
    private String unitName;
    private String countryName;

    public Card(String countryName, int index) {
        char[] unitTypes= {'I','C','A'};
        String[] unitNames= {"Infantry","Cavalry","Artillery"};
        this.countryName = countryName;
        this.unitType = unitTypes[index];
        this.unitName = unitNames[index];
    };
    
    /*Accessor Methods*/
    public String getCountryName() {
        return countryName;
    }
    
    public char getUnitType() {
        return unitType;
    }
    
    /*Mutator Methods*/
    public void setUnitType(char unitType) {
        this.unitType = unitType;
    }
    
    public void setCountryName(String countryName) {
    	validateCard(countryName);
        this.countryName = countryName;
    }

	@Override
   	public String toString() {
   		return "[" + unitName + " " + countryName + "]";
   	}
	
	 private void validateCard(String name) {
	    if (name.trim().isEmpty()) {
	    	throw new IllegalArgumentException("Country name should not be empty");
	    }
	}
}
