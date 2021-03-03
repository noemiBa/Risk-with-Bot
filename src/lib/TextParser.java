package lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
	
	/* An array containing all of the patterns for the game to recognise, and the Names of the countries the refer to. 
	 * The Java library Regex Pattern was used to create the patterns. 
	 * 
	 * Note: the . wild card tells the program to expect a character, and the {min,max} in the cury brackets indicates the minimum
	 * and maximum number of characters to be expected (where the maximum is equivalent to the length of the full string Name.
	 * The escape sequence \s indicates the presence of a space.
	 */
	public static PatternName [] patterns = {
			new PatternName(Pattern.compile("ON.*?", Pattern.CASE_INSENSITIVE), "Ontario"),
			new PatternName(Pattern.compile("(QUE.*?)|(Q.{0,4}C.*?)", Pattern.CASE_INSENSITIVE), "Quebec"),
			new PatternName(Pattern.compile("(NW.*?)|(NWT.*?)|(North\\s?West.*?)|(North\\s?W.*?)", Pattern.CASE_INSENSITIVE), "NW Territory"),
			new PatternName(Pattern.compile("(ALTA.*?)|(AB.*?)|(ALB.*?)", Pattern.CASE_INSENSITIVE), "Alberta"),
			new PatternName(Pattern.compile("(G.{0,4}L.*?)|(Green.*?)", Pattern.CASE_INSENSITIVE), "Greenland"),
			new PatternName(Pattern.compile("(E\\s?US.*?)|(E\\s?Un.*?|(East\\s?US.*?)|(East\\s?Un.*?))", Pattern.CASE_INSENSITIVE), "E United States"),
			new PatternName(Pattern.compile("(W\\s?US.*?)|(W\\sUn.*?)|(West\\s?US.*?)|(West\\s?Un.*?)", Pattern.CASE_INSENSITIVE), "W United States"),
			new PatternName(Pattern.compile("(CE.{0,7}A.*?)|(CA.*?)|(C\\s?AM.*?)", Pattern.CASE_INSENSITIVE), "Central America"),
			new PatternName(Pattern.compile("(A.{0,3}K.*?)|ALAS.*?", Pattern.CASE_INSENSITIVE), "Alaska"),
			new PatternName(Pattern.compile("(G.{0,5}B.*?)|(ENG.*?)|(United\\sKingdom.*?)",Pattern.CASE_INSENSITIVE), "Great Britain"), //10
			new PatternName(Pattern.compile("(W\\s?EU.*?)|(West\\s?EU.*?)", Pattern.CASE_INSENSITIVE), "W Europe"),
			new PatternName(Pattern.compile("(S\\s?EU.*?)|(South\\s?EU.*?)", Pattern.CASE_INSENSITIVE), "S Europe"),
			new PatternName(Pattern.compile("(UKRA.*?)|(UKR.*?)|(UA.*?)", Pattern.CASE_INSENSITIVE), "Ukraine"),
			new PatternName(Pattern.compile("(N\\s?EU.*?)|(North\\s?EU.*?)", Pattern.CASE_INSENSITIVE), "N Europe"),
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
			new PatternName(Pattern.compile("(CH.{0,1}N.*?)|(CH.*?)", Pattern.CASE_INSENSITIVE), "China"),
			new PatternName(Pattern.compile("(E\\s?AU.*?)|(East\\s?AU.*?)", Pattern.CASE_INSENSITIVE), "E Australia"),
			new PatternName(Pattern.compile("(PNG.*?)|(NG.*?)|(N.{0,3}G.*?)", Pattern.CASE_INSENSITIVE), "New Guinea"), //30
			new PatternName(Pattern.compile("(W\\s?AU.*?)|(West\\s?AU.*?)", Pattern.CASE_INSENSITIVE), "W Australia"),
			new PatternName(Pattern.compile("(I.{0,1}DO.*?)|ID.*?", Pattern.CASE_INSENSITIVE), "Indonesia"),
			new PatternName(Pattern.compile("VE.*?", Pattern.CASE_INSENSITIVE), "Venezuela"),
			new PatternName(Pattern.compile("PE.*?", Pattern.CASE_INSENSITIVE), "Peru"),
			new PatternName(Pattern.compile("BR.*?", Pattern.CASE_INSENSITIVE), "Brazil"),
			new PatternName(Pattern.compile("AR.*?", Pattern.CASE_INSENSITIVE), "Argentina"),
			new PatternName(Pattern.compile("(CON.*?)|(CG.*?)", Pattern.CASE_INSENSITIVE), "Congo"),
			new PatternName(Pattern.compile("(N\\s?AF.*?)|(North\\s?AF.*?)", Pattern.CASE_INSENSITIVE), "N Africa"),
			new PatternName(Pattern.compile("(S\\s?AF.*?)|(South\\s?AF.*?)", Pattern.CASE_INSENSITIVE), "S Africa"),
			new PatternName(Pattern.compile("EG.*?", Pattern.CASE_INSENSITIVE), "Egypt"), //40
			new PatternName(Pattern.compile("(E\\s?AF.*?)|(East\\s?AF.*?)", Pattern.CASE_INSENSITIVE), "E Africa"),
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
		String output = "";
		for (int i = 0; i < patterns.length; i++) {
			Matcher matcher = patterns[i].getPattern().matcher(input);
			if (matcher.matches()) {
				return output = patterns[i].getName();
			}
		}
		throw new IllegalArgumentException();
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
