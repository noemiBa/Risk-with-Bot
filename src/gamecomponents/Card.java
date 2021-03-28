	package gamecomponents;

/** Creates a new Card object with the card's countryName and unitType
 * The Deck class uses this card object by creating a deck as an ArrayList of 42 Card objects
 * each with their own countryName and unityType
 * */
public class Card
{
	public static char[] unitTypes= {'I','C','A'};
    private String countryName;
    private char unitType;

    public Card(String countryName, char unitType) {
        this.countryName = countryName;
        this.unitType = unitType;
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
   		return "[countryName=" + countryName + ", unitType=" + unitType + "]";
   	}
	
	 private void validateCard(String name) {
	    if (name.trim().isEmpty()) {
	    	throw new IllegalArgumentException("Country name should not be empty");
	    }
	}
    
    
}
