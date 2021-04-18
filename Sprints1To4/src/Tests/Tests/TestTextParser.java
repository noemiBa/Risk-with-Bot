package Tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.regex.Matcher;
import org.junit.jupiter.api.Test;
import lib.TextParser; 

/* This class tests the text parsing when the user enters a shortened name for a Country.
 * Firstly, the parse() method is tested more generally. 
 * After that, the parsing for each Country name is tested individually.
 * 
 */
class TestTextParser {

	Matcher matcher; 
	TextParser.PatternName[] patterns = TextParser.patterns; 
	
	@Test
	void TestParseMethod() {
		assertEquals(TextParser.parse("Ont"), "Ontario"); 
		assertEquals(TextParser.parse("Indi"), "India");
		assertEquals(TextParser.parse("Indo"), "Indonesia"); 
		assertEquals(TextParser.parse("NWT"), "NW Territory"); 
	}
	
	@Test
	void testParseOntario() {
		matcher = patterns[0].getPattern().matcher("ON");
		assertTrue(matcher.matches());
		
		matcher = patterns[0].getPattern().matcher("ONtario");
		assertTrue(matcher.matches());
		
		matcher = patterns[0].getPattern().matcher("ont");
		assertTrue(matcher.matches());
		
		matcher = patterns[0].getPattern().matcher("ostario");
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseQuebec() {
		matcher = patterns[1].getPattern().matcher("QUE");
		assertTrue(matcher.matches());
		
		matcher = patterns[1].getPattern().matcher("QC");
		assertTrue(matcher.matches());
		
		matcher = patterns[1].getPattern().matcher("quebe");
		assertTrue(matcher.matches());
		
		matcher = patterns[1].getPattern().matcher("Q"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseNWTerritory() {
		matcher = patterns[2].getPattern().matcher("NW");
		assertTrue(matcher.matches());
		
		matcher = patterns[2].getPattern().matcher("NWT");
		assertTrue(matcher.matches());
		
		matcher = patterns[2].getPattern().matcher("North W");
		assertTrue(matcher.matches());
		
		matcher = patterns[2].getPattern().matcher("NOrth West Territory");
		assertTrue(matcher.matches());
		
		matcher = patterns[2].getPattern().matcher("N "); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseAlberta() {
		matcher = patterns[3].getPattern().matcher("ALTA");
		assertTrue(matcher.matches());
		
		matcher = patterns[3].getPattern().matcher("Alb");
		assertTrue(matcher.matches());
		
		matcher = patterns[3].getPattern().matcher("AB");
		assertTrue(matcher.matches());
		
		matcher = patterns[3].getPattern().matcher("Albe");
		assertTrue(matcher.matches());
		
		matcher = patterns[3].getPattern().matcher("A"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseGreenland() {
		matcher = patterns[4].getPattern().matcher("Green");
		assertTrue(matcher.matches());
		
		matcher = patterns[4].getPattern().matcher("GL");
		assertTrue(matcher.matches());
		
		matcher = patterns[4].getPattern().matcher("GLand");
		assertTrue(matcher.matches());
		
		matcher = patterns[4].getPattern().matcher("G"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseEUnitedS() {
		matcher = patterns[5].getPattern().matcher("E USA");
		assertTrue(matcher.matches());
		
		matcher = patterns[5].getPattern().matcher("East US");
		assertTrue(matcher.matches());
		
		matcher = patterns[5].getPattern().matcher("E United");
		assertTrue(matcher.matches());
		
		matcher = patterns[5].getPattern().matcher("E U"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	
	@Test
	void testParseWUnitedS() {
		matcher = patterns[6].getPattern().matcher("WUSA");
		assertTrue(matcher.matches());
		
		matcher = patterns[6].getPattern().matcher("West US");
		assertTrue(matcher.matches());
		
		matcher = patterns[6].getPattern().matcher("W United");
		assertTrue(matcher.matches());
		
		matcher = patterns[6].getPattern().matcher("W U"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseCentralAmerica() {
		matcher = patterns[7].getPattern().matcher("CA");
		assertTrue(matcher.matches());
		
		matcher = patterns[7].getPattern().matcher("Central A");
		assertTrue(matcher.matches());
		
		matcher = patterns[7].getPattern().matcher("C America");
		assertTrue(matcher.matches());
		
		matcher = patterns[7].getPattern().matcher("C"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseAlaska() {
		matcher = patterns[8].getPattern().matcher("AK");
		assertTrue(matcher.matches());
		
		matcher = patterns[8].getPattern().matcher("Alas");
		assertTrue(matcher.matches());
		
		matcher = patterns[8].getPattern().matcher("Al"); //not unambiguous 
		assertFalse(matcher.matches());
	}

	@Test
	void testParseGreatBritain() {
		matcher = patterns[9].getPattern().matcher("GB");
		assertTrue(matcher.matches());
		
		matcher = patterns[9].getPattern().matcher("ENG");
		assertTrue(matcher.matches());
		
		matcher = patterns[9].getPattern().matcher("United Kingdom");
		assertTrue(matcher.matches());
		
		matcher = patterns[9].getPattern().matcher("Great Britain");
		assertTrue(matcher.matches());
		
		matcher = patterns[9].getPattern().matcher("Great B");
		assertTrue(matcher.matches());
		
		matcher = patterns[9].getPattern().matcher("Gr"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseWEurope() {
		matcher = patterns[10].getPattern().matcher("W Eu");
		assertTrue(matcher.matches());
		
		matcher = patterns[10].getPattern().matcher("West Eu");
		assertTrue(matcher.matches());
		
		matcher = patterns[10].getPattern().matcher("W EU");
		assertTrue(matcher.matches());
		
		matcher = patterns[10].getPattern().matcher("W E"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseSEurope() {
		matcher = patterns[11].getPattern().matcher("S EU");
		assertTrue(matcher.matches());
		
		matcher = patterns[11].getPattern().matcher("South Eu");
		assertTrue(matcher.matches());
		
		matcher = patterns[11].getPattern().matcher("S eu");
		assertTrue(matcher.matches());
		
		matcher = patterns[11].getPattern().matcher("S E"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseUkraine() {
		matcher = patterns[12].getPattern().matcher("UA");
		assertTrue(matcher.matches());
		
		matcher = patterns[12].getPattern().matcher("Ukr");
		assertTrue(matcher.matches());
		
		matcher = patterns[12].getPattern().matcher("Ukra");
		assertTrue(matcher.matches());
		
		matcher = patterns[12].getPattern().matcher("UK"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseNEurope() {
		matcher = patterns[13].getPattern().matcher("N EU");
		assertTrue(matcher.matches());
		
		matcher = patterns[13].getPattern().matcher("North Eu");
		assertTrue(matcher.matches());
		
		matcher = patterns[13].getPattern().matcher("N eu");
		assertTrue(matcher.matches());
		
		matcher = patterns[13].getPattern().matcher("N E"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseIceland() {
		matcher = patterns[14].getPattern().matcher("Ice");
		assertTrue(matcher.matches());
		
		matcher = patterns[14].getPattern().matcher("IceL");
		assertTrue(matcher.matches());
		
		matcher = patterns[14].getPattern().matcher("ISL");
		assertTrue(matcher.matches());
		
		matcher = patterns[14].getPattern().matcher("Ic"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseScandinavia() {
		matcher = patterns[15].getPattern().matcher("Scand");
		assertTrue(matcher.matches());
		
		matcher = patterns[15].getPattern().matcher("Scan");
		assertTrue(matcher.matches());
		
		matcher = patterns[15].getPattern().matcher("Scandinavia");
		assertTrue(matcher.matches());
		
		matcher = patterns[15].getPattern().matcher("Sc"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseAfghanistan() {
		matcher = patterns[16].getPattern().matcher("Af");
		assertTrue(matcher.matches());
		
		matcher = patterns[16].getPattern().matcher("Afghan");
		assertTrue(matcher.matches());
		
		matcher = patterns[16].getPattern().matcher("Afghanistan");
		assertTrue(matcher.matches());
		
		matcher = patterns[16].getPattern().matcher("A"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseIndia() {
		matcher = patterns[17].getPattern().matcher("INdi");
		assertTrue(matcher.matches());
		
		matcher = patterns[17].getPattern().matcher("India");
		assertTrue(matcher.matches());
		
		matcher = patterns[17].getPattern().matcher("Ind"); //not unambiguous 
		assertFalse(matcher.matches());
	}

	@Test
	void testParseMiddleEast() {
		matcher = patterns[18].getPattern().matcher("ME");
		assertTrue(matcher.matches());
		
		matcher = patterns[18].getPattern().matcher("Middle East");
		assertTrue(matcher.matches());
		
		matcher = patterns[18].getPattern().matcher("Mid E");
		assertTrue(matcher.matches());
		
		matcher = patterns[18].getPattern().matcher("Mi"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseJapan() {
		matcher = patterns[19].getPattern().matcher("JP");
		assertTrue(matcher.matches());
		
		matcher = patterns[19].getPattern().matcher("Jap");
		assertTrue(matcher.matches());
		
		matcher = patterns[19].getPattern().matcher("Japan");
		assertTrue(matcher.matches());
		
		matcher = patterns[19].getPattern().matcher("J"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseUral() {
		matcher = patterns[20].getPattern().matcher("Ural");
		assertTrue(matcher.matches());
		
		matcher = patterns[20].getPattern().matcher("Ura");
		assertTrue(matcher.matches());
		
		matcher = patterns[20].getPattern().matcher("UR");
		assertTrue(matcher.matches());
		
		matcher = patterns[20].getPattern().matcher("U"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseYakutsk() {
		matcher = patterns[21].getPattern().matcher("Yak");
		assertTrue(matcher.matches());
		
		matcher = patterns[21].getPattern().matcher("YAKT");
		assertTrue(matcher.matches());
		
		matcher = patterns[21].getPattern().matcher("Yakutsk");
		assertTrue(matcher.matches());
		
		matcher = patterns[21].getPattern().matcher("Y"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseKamchatka() {
		matcher = patterns[22].getPattern().matcher("KAM");
		assertTrue(matcher.matches());
		
		matcher = patterns[22].getPattern().matcher("Kamcha");
		assertTrue(matcher.matches());
		
		matcher = patterns[22].getPattern().matcher("KAmchatka");
		assertTrue(matcher.matches());
		
		matcher = patterns[22].getPattern().matcher("K"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseSiam() {
		matcher = patterns[23].getPattern().matcher("Siam");
		assertTrue(matcher.matches());
		
		matcher = patterns[23].getPattern().matcher("Si"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseIrkutsk() {
		matcher = patterns[24].getPattern().matcher("IRKT");
		assertTrue(matcher.matches());
		
		matcher = patterns[24].getPattern().matcher("IRK");
		assertTrue(matcher.matches());
		
		matcher = patterns[24].getPattern().matcher("Irkutsk");
		assertTrue(matcher.matches());
		
		matcher = patterns[24].getPattern().matcher("I"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseSiberia() {
		matcher = patterns[25].getPattern().matcher("SI");
		assertTrue(matcher.matches());
		
		matcher = patterns[25].getPattern().matcher("Sib");
		assertTrue(matcher.matches());
		
		matcher = patterns[25].getPattern().matcher("Siberia");
		assertTrue(matcher.matches());
		
		matcher = patterns[25].getPattern().matcher("S"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseMongolia() {
		matcher = patterns[26].getPattern().matcher("MN");
		assertTrue(matcher.matches());
		
		matcher = patterns[26].getPattern().matcher("Mon");
		assertTrue(matcher.matches());
		
		matcher = patterns[26].getPattern().matcher("Mongolia");
		assertTrue(matcher.matches());
		
		matcher = patterns[26].getPattern().matcher("M"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseChina() {
		
		matcher = patterns[27].getPattern().matcher("CHi");
		assertTrue(matcher.matches());
		
		matcher = patterns[27].getPattern().matcher("China");
		assertTrue(matcher.matches());
		
		matcher = patterns[27].getPattern().matcher("C"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseEAustralia() {
		matcher = patterns[28].getPattern().matcher("E AU");
		assertTrue(matcher.matches());
		
		matcher = patterns[28].getPattern().matcher("East AU");
		assertTrue(matcher.matches());
		
		matcher = patterns[28].getPattern().matcher("East Australia");
		assertTrue(matcher.matches());
		
		matcher = patterns[28].getPattern().matcher("EA"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseNewGuinea() {
		matcher = patterns[29].getPattern().matcher("PNG");
		assertTrue(matcher.matches());
		
		matcher = patterns[29].getPattern().matcher("NG");
		assertTrue(matcher.matches());
		
		matcher = patterns[29].getPattern().matcher("N Guinea");
		assertTrue(matcher.matches());
		
		matcher = patterns[29].getPattern().matcher("Ne"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseWAustralia() {
		matcher = patterns[30].getPattern().matcher("W AU");
		assertTrue(matcher.matches());
		
		matcher = patterns[30].getPattern().matcher("West AU");
		assertTrue(matcher.matches());
		
		matcher = patterns[30].getPattern().matcher("West Australia");
		assertTrue(matcher.matches());
		
		matcher = patterns[30].getPattern().matcher("WA"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseIndonesia() {
		matcher = patterns[31].getPattern().matcher("Id");
		assertTrue(matcher.matches());
		
		matcher = patterns[31].getPattern().matcher("Indo");
		assertTrue(matcher.matches());
		
		matcher = patterns[31].getPattern().matcher("Indonesia");
		assertTrue(matcher.matches());
		
		matcher = patterns[31].getPattern().matcher("Ind"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseVenezuela() {
		matcher = patterns[32].getPattern().matcher("Ve");
		assertTrue(matcher.matches());
		
		matcher = patterns[32].getPattern().matcher("Venez");
		assertTrue(matcher.matches());
		
		matcher = patterns[32].getPattern().matcher("Venezuela");
		assertTrue(matcher.matches());
		
		matcher = patterns[32].getPattern().matcher("V"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParsePeru() {
		matcher = patterns[33].getPattern().matcher("Pe");
		assertTrue(matcher.matches());
		
		matcher = patterns[33].getPattern().matcher("Per");
		assertTrue(matcher.matches());
		
		matcher = patterns[33].getPattern().matcher("PEru");
		assertTrue(matcher.matches());
		
		matcher = patterns[33].getPattern().matcher("P"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseBrazil() {
		matcher = patterns[34].getPattern().matcher("Br");
		assertTrue(matcher.matches());
		
		matcher = patterns[34].getPattern().matcher("Braz");
		assertTrue(matcher.matches());
		
		matcher = patterns[34].getPattern().matcher("Brazil");
		assertTrue(matcher.matches());
		
		matcher = patterns[34].getPattern().matcher("B"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseArgentina() {
		matcher = patterns[35].getPattern().matcher("Ar");
		assertTrue(matcher.matches());
		
		matcher = patterns[35].getPattern().matcher("Argen");
		assertTrue(matcher.matches());
		
		matcher = patterns[35].getPattern().matcher("Argentina");
		assertTrue(matcher.matches());
		
		matcher = patterns[35].getPattern().matcher("A"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseCongo() {
		matcher = patterns[36].getPattern().matcher("Congo");
		assertTrue(matcher.matches());
		
		matcher = patterns[36].getPattern().matcher("CG");
		assertTrue(matcher.matches());
		
		matcher = patterns[36].getPattern().matcher("Co"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseNAfrica() {
		matcher = patterns[37].getPattern().matcher("N Af");
		assertTrue(matcher.matches());
		
		matcher = patterns[37].getPattern().matcher("North Africa");
		assertTrue(matcher.matches());
		
		matcher = patterns[37].getPattern().matcher("North aF");
		assertTrue(matcher.matches());
		
		matcher = patterns[37].getPattern().matcher("N A"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseSAfrica() {
		matcher = patterns[38].getPattern().matcher("S AF");
		assertTrue(matcher.matches());
		
		matcher = patterns[38].getPattern().matcher("south Africa");
		assertTrue(matcher.matches());
		
		matcher = patterns[38].getPattern().matcher("south Afr");
		assertTrue(matcher.matches());
		
		matcher = patterns[38].getPattern().matcher("s a"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseEgypt() {
		matcher = patterns[39].getPattern().matcher("Eg");
		assertTrue(matcher.matches());
		
		matcher = patterns[39].getPattern().matcher("Egypt");
		assertTrue(matcher.matches());
		
		matcher = patterns[39].getPattern().matcher("Egy");
		assertTrue(matcher.matches());
		
		matcher = patterns[39].getPattern().matcher("E"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseEAfrica() {
		matcher = patterns[40].getPattern().matcher("E Af");
		assertTrue(matcher.matches());
		
		matcher = patterns[40].getPattern().matcher("East Africa");
		assertTrue(matcher.matches());
		
		matcher = patterns[40].getPattern().matcher("East Afr");
		assertTrue(matcher.matches());
		
		matcher = patterns[40].getPattern().matcher("E A"); //not unambiguous 
		assertFalse(matcher.matches());
	}
	
	@Test
	void testParseMadagascar() {
		matcher = patterns[41].getPattern().matcher("MAd");
		assertTrue(matcher.matches());
		
		matcher = patterns[41].getPattern().matcher("MG");
		assertTrue(matcher.matches());
		
		matcher = patterns[41].getPattern().matcher("Madagascar");
		assertTrue(matcher.matches());
		
		matcher = patterns[41].getPattern().matcher("M"); //not unambiguous 
		assertFalse(matcher.matches());
	}
}
