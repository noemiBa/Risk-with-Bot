package gamecomponents;

/** Creates a new Card object with the card's countryName and unitType
 * The Deck class uses this card object by creating a deck as an ArrayList of 42 Card objects
 * each with their own countryName and unityType
 * */
public class Card
{

	public static enum type{INFANTRY, CAVALRY, ARTILLERY};
    private String countryName;
    private type unitType;

    public Card(String countryName, type unitType)
    {
        this.countryName = countryName;
        this.unitType = unitType;
    };

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public type getUnitType()
    {
        return unitType;
    }

    public void setUnitType(type unitType)
    {
        this.unitType = unitType;
    }
    
    @Override
   	public String toString() {
   		return "[countryName=" + countryName + ", unitType=" + unitType + "]";
   	}
}
