package lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
	
	/* An array containing all of the patterns for the game to recognise, and the Names of the countries the refer to. 
	 * The Java library Regex Pattern was used to create the patterns. 
	 * 
	 * Note: the . wild card tells the program to expect a character, and the *? indicates that the number of characters to expect is unknown
	 * The escape sequence \s indicates the presence of a space.
	 */
	public static PatternName [] patterns = {
			new PatternName(Pattern.compile("ON.*?", Pattern.CASE_INSENSITIVE), "Ontario"),
			new PatternName(Pattern.compile("(QUE.*?)|(Q.{0,4}C.*?)", Pattern.CASE_INSENSITIVE), "Quebec"),
			new PatternName(Pattern.compile("(NW.*?)|(NWT.*?)|(North\\sWest.*?)|(North\\sW.*?)", Pattern.CASE_INSENSITIVE), "NW Territory"),
			new PatternName(Pattern.compile("(ALTA.*?)|(AB.*?)|(ALB.*?)", Pattern.CASE_INSENSITIVE), "Alberta"),
			new PatternName(Pattern.compile("(G.{0,4}L.*?)|(Green.*?)", Pattern.CASE_INSENSITIVE), "Greenland"),
			new PatternName(Pattern.compile("(E\\sUS.*?)|(E\\sUn.*?|(East\\sUS.*?)|(East\\sUn.*?))", Pattern.CASE_INSENSITIVE), "E United States"),
			new PatternName(Pattern.compile("(W\\sUS.*?)|(W\\sUn.*?)|(West\\sUS.*?)|(West\\sUn.*?)", Pattern.CASE_INSENSITIVE), "W United States"),
			new PatternName(Pattern.compile("(CE.{0,7}A.*?)|(CA.*?)|(C\\sAM.*?)", Pattern.CASE_INSENSITIVE), "Central America"),
			new PatternName(Pattern.compile("(A.{0,3}K.*?)|ALAS.*?", Pattern.CASE_INSENSITIVE), "Alaska"),
			new PatternName(Pattern.compile("(G.{0,5}B.*?)|(UK.*?)|(ENG.*?)|(United\\sKingdom.*?)",Pattern.CASE_INSENSITIVE), "Great Britain"), //10
			new PatternName(Pattern.compile("(W\\sEU.*?)|(West\\sEU.*?)", Pattern.CASE_INSENSITIVE), "W Europe"),
			new PatternName(Pattern.compile("(S\\sEU.*?)|(South\\sEU.*?)", Pattern.CASE_INSENSITIVE), "S Europe"),
			new PatternName(Pattern.compile("(UK.{0,2}A.*?)|(UKR.*?)|(UA.*?)", Pattern.CASE_INSENSITIVE), "Ukraine"),
			new PatternName(Pattern.compile("(N\\sEU.*?)|(North\\sEU.*?)", Pattern.CASE_INSENSITIVE), "N Europe"),
			new PatternName(Pattern.compile("(ISL.*?)|(ICE.*?)", Pattern.CASE_INSENSITIVE), "Iceland"),
			new PatternName(Pattern.compile("Scan.*?", Pattern.CASE_INSENSITIVE), "Scandinavia"),
			new PatternName(Pattern.compile("AF.*?", Pattern.CASE_INSENSITIVE), "Afghanistan"),
			new PatternName(Pattern.compile("INDI.*?", Pattern.CASE_INSENSITIVE), "India"),
			new PatternName(Pattern.compile("M.{0,6}E.*?", Pattern.CASE_INSENSITIVE), "Middle East"),
			new PatternName(Pattern.compile("J.{0,1}P.*?", Pattern.CASE_INSENSITIVE), "Japan"),//20
			new PatternName(Pattern.compile("Ur.*?", Pattern.CASE_INSENSITIVE), "Ural"),
			new PatternName(Pattern.compile("Yak.*?", Pattern.CASE_INSENSITIVE), "Yakutsk"),
			new PatternName(Pattern.compile("Kam.*?", Pattern.CASE_INSENSITIVE), "Kamchatka"),
			new PatternName(Pattern.compile("Siam.*?", Pattern.CASE_INSENSITIVE), "Siam"),
			new PatternName(Pattern.compile("Irk.*?", Pattern.CASE_INSENSITIVE), "Irkutsk"),
			new PatternName(Pattern.compile("SI.*?", Pattern.CASE_INSENSITIVE), "Siberia"),
			new PatternName(Pattern.compile("M.{0,1}N.*?", Pattern.CASE_INSENSITIVE), "Mongolia"),
			new PatternName(Pattern.compile("(C.{0,2}N.*?)|(CH.*?)", Pattern.CASE_INSENSITIVE), "China"),
			new PatternName(Pattern.compile("(E\\sAU.*?)|(East\\sAU.*?)", Pattern.CASE_INSENSITIVE), "E Australia"),
			new PatternName(Pattern.compile("(PNG.*?)|(NG.*?)|(N.{0,3}G.*?)", Pattern.CASE_INSENSITIVE), "New Guinea"), //30
			new PatternName(Pattern.compile("(W\\sAU.*?)|(West\\sAU.*?)", Pattern.CASE_INSENSITIVE), "W Australia"),
			new PatternName(Pattern.compile("(I.{0,1}DO.*?)|ID.*?", Pattern.CASE_INSENSITIVE), "Indonesia"),
			new PatternName(Pattern.compile("VE.*?", Pattern.CASE_INSENSITIVE), "Venezuela"),
			new PatternName(Pattern.compile("PE.*?", Pattern.CASE_INSENSITIVE), "Peru"),
			new PatternName(Pattern.compile("BR.*?", Pattern.CASE_INSENSITIVE), "Brazil"),
			new PatternName(Pattern.compile("AR.*?", Pattern.CASE_INSENSITIVE), "Argentina"),
			new PatternName(Pattern.compile("C.{0,2}G.*?", Pattern.CASE_INSENSITIVE), "Congo"),
			new PatternName(Pattern.compile("(N\\sAF.*?)|(North\\sAF.*?)", Pattern.CASE_INSENSITIVE), "N Africa"),
			new PatternName(Pattern.compile("(S\\sAF.*?)|(South\\sAF.*?)", Pattern.CASE_INSENSITIVE), "S Africa"),
			new PatternName(Pattern.compile("EG.*?", Pattern.CASE_INSENSITIVE), "Egypt"), //40
			new PatternName(Pattern.compile("(E\\sAF.*?)|(East\\sAF.*?)", Pattern.CASE_INSENSITIVE), "E Africa"),
			new PatternName(Pattern.compile("MG.*?|(MAD.*?)", Pattern.CASE_INSENSITIVE), "Madagascar")
	};
	
	/* This method takes in the shortened (or full length) version of the country name entered by the user, 
	 * and returns the full name to be used in the CustomArrayList as a key. It returns the string "Error" 
	 * if the name could not be parsed.
	 * 
	 * @Param input: the input entered by the user. 
	 * @Return: the full parsed name, or "Error"
	 */
	public static String parse(String input) {
		String output = "Error";
		
		for (int i=0; i<patterns.length; i++) {
			Matcher matcher = patterns[i].getPattern().matcher(input);
			if (matcher.matches()) {
				return output = patterns[i].getName();
			}
		}
		
		return output; 
	}
	
	/* Nested inner class PatternName, contains the pattern to be recognised at the name of a 
	 * certain Country, and said Country name
	 */
	public static final class PatternName {
		String name;         //the name of the Country
		Pattern pattern;     //the pattern to be recognised by the TextParser
		public PatternName(Pattern pattern, String name) {
			this.name = name; 
			this.pattern = pattern; 
		}

		public String getName() {
			return name;
		}

		public Pattern getPattern() {
			return pattern;
		}
	}
}
