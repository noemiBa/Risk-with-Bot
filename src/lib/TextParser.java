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
			new PatternName(Pattern.compile("ON.{0,5}", Pattern.CASE_INSENSITIVE), "Ontario"),
			new PatternName(Pattern.compile("(QUE.{0,3})|(Q.{0,4}C)", Pattern.CASE_INSENSITIVE), "Quebec"),
			new PatternName(Pattern.compile("(NW.{0,10})|(NWT)|(North\sWest.{1,10})|(North\sW.{0,13})", Pattern.CASE_INSENSITIVE), "NW Territory"),
			new PatternName(Pattern.compile("(ALTA)|(AB)|(AL.{0,5})", Pattern.CASE_INSENSITIVE), "Alberta"),
			new PatternName(Pattern.compile("(G.{0,4}L.{0,3})|Green", Pattern.CASE_INSENSITIVE), "Greenland"),
			new PatternName(Pattern.compile("(E\sUS.{0,10})|(E\sUn.{0,10}|(East\sUS.{0,10})|(East\sUn.{0,10}))", Pattern.CASE_INSENSITIVE), "E United States"),
			new PatternName(Pattern.compile("(W\\sUS.{0,10})|(W\\sUn.{0,10})|(West\\sUS.{0,10})|(West\\sUn.{0,10})", Pattern.CASE_INSENSITIVE), "W United States"),
			new PatternName(Pattern.compile("C.{0,7}A.{0,6}", Pattern.CASE_INSENSITIVE), "Central America"),
			new PatternName(Pattern.compile("(A.{0,3}K.{0,1})|ALAS.{0,2}", Pattern.CASE_INSENSITIVE), "Alaska"),
			new PatternName(Pattern.compile("(G.{0,5}B.{0,6})|(UK)|(ENG)|(United\\sKingdom)",Pattern.CASE_INSENSITIVE), "Great Britain"), //10
			new PatternName(Pattern.compile("(W\\sEU.{0,4})|(West\\sEU.{0,4})", Pattern.CASE_INSENSITIVE), "W Europe"),
			new PatternName(Pattern.compile("(S\\sEU.{0,4})|(South\\sEU.{0,4})", Pattern.CASE_INSENSITIVE), "S Europe"),
			new PatternName(Pattern.compile("(U.{0,2}A.{0,3})|UKR", Pattern.CASE_INSENSITIVE), "Ukraine"),
			new PatternName(Pattern.compile("(N\\sEU.{0,4})|(North\\sEU.{0,4})", Pattern.CASE_INSENSITIVE), "N Europe"),
			new PatternName(Pattern.compile("(ISL)|(ICE.{0,4})", Pattern.CASE_INSENSITIVE), "Iceland"),
			new PatternName(Pattern.compile("Scan.{0,7}", Pattern.CASE_INSENSITIVE), "Scandinavia"),
			new PatternName(Pattern.compile("AF.{0,9}", Pattern.CASE_INSENSITIVE), "Afghanistan"),
			new PatternName(Pattern.compile("INDI.{0,1}", Pattern.CASE_INSENSITIVE), "India"),
			new PatternName(Pattern.compile("M.{0,6}E.{0,3}", Pattern.CASE_INSENSITIVE), "Middle East"),
			new PatternName(Pattern.compile("J.{0,1}P.{0,2}", Pattern.CASE_INSENSITIVE), "Japan"),//20
			new PatternName(Pattern.compile("Ur.{0,2}", Pattern.CASE_INSENSITIVE), "Ural"),
			new PatternName(Pattern.compile("Yak.{0,4}", Pattern.CASE_INSENSITIVE), "Yakutsk"),
			new PatternName(Pattern.compile("Kam.{0,6}", Pattern.CASE_INSENSITIVE), "Kamchakta"),
			new PatternName(Pattern.compile("Siam", Pattern.CASE_INSENSITIVE), "Siam"),
			new PatternName(Pattern.compile("Irk.{0,4}", Pattern.CASE_INSENSITIVE), "Irkustks"),
			new PatternName(Pattern.compile("SI.{0,5}", Pattern.CASE_INSENSITIVE), "Siberia"),
			new PatternName(Pattern.compile("M.{0,1}N.{0,5}", Pattern.CASE_INSENSITIVE), "Mongolia"),
			new PatternName(Pattern.compile("(C.{0,2}N.{0,1})|(CH.{0,3})", Pattern.CASE_INSENSITIVE), "China"),
			new PatternName(Pattern.compile("(E\\sAU.{0,7})|(East\\sAU.{0,7})", Pattern.CASE_INSENSITIVE), "E Australia"),
			new PatternName(Pattern.compile("(PNG)|(NG)|(N.{0,3}G.{0,5})", Pattern.CASE_INSENSITIVE), "New Guinea"), //30
			new PatternName(Pattern.compile("(W\\sAU.{0,7})|(West\\sAU.{0,7})", Pattern.CASE_INSENSITIVE), "W Australia"),
			new PatternName(Pattern.compile("(I.{0,1}DO.{0,5})|ID", Pattern.CASE_INSENSITIVE), "Indonesia"),
			new PatternName(Pattern.compile("VE.{0,7}", Pattern.CASE_INSENSITIVE), "Venezuela"),
			new PatternName(Pattern.compile("PE.{0,2}", Pattern.CASE_INSENSITIVE), "Peru"),
			new PatternName(Pattern.compile("BR.{0,4}", Pattern.CASE_INSENSITIVE), "Brazi"),
			new PatternName(Pattern.compile("AR.{0,7}", Pattern.CASE_INSENSITIVE), "Argentina"),
			new PatternName(Pattern.compile("C.{0,2}G.{0,1}", Pattern.CASE_INSENSITIVE), "Congo"),
			new PatternName(Pattern.compile("(N\\sAF.{0,4})|(North\\sAF.{0,4})", Pattern.CASE_INSENSITIVE), "N Africa"),
			new PatternName(Pattern.compile("(S\\sAF.{0,4})|(South\\sAF.{0,4})", Pattern.CASE_INSENSITIVE), "S Africa"),
			new PatternName(Pattern.compile("EG.{0,3}", Pattern.CASE_INSENSITIVE), "Egypt"), //40
			new PatternName(Pattern.compile("(E\\sAF.{0,4})|(East\\sAF.{0,4})", Pattern.CASE_INSENSITIVE), "E Africa"),
			new PatternName(Pattern.compile("MG|(MAD.{0,7})", Pattern.CASE_INSENSITIVE), "Madagascar")
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
