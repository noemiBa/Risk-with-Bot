package gamecomponents;

import java.awt.Color;

import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;

public class GameData {
	public static final int NUM_PLAYERS = 2;
	public static final int NUM_NEUTRALS = 4;
	public static final int TOT_NUM_PLAYERS = NUM_PLAYERS + NUM_NEUTRALS;
	public static final int NUM_COUNTRIES = 42;
	public static final int INIT_COUNTRIES_PLAYER = 9;
	public static final int INIT_COUNTRIES_NEUTRAL = 6;
	
	public static final String[] COUNTRY_NAMES = {
            "Ontario", "Quebec", "NW Territory", "Alberta", "Greenland", "E United States", "W United States", "Central America", "Alaska",
            "Great Britain", "W Europe", "S Europe", "Ukraine", "N Europe", "Iceland", "Scandinavia",
            "Afghanistan", "India", "Middle East", "Japan", "Ural", "Yakutsk", "Kamchatka", "Siam", "Irkutsk", "Siberia", "Mongolia", "China",
            "E Australia", "New Guinea", "W Australia", "Indonesia", "Venezuela", "Peru", "Brazil", "Argentina",
            "Congo", "N Africa", "S Africa", "Egypt", "E Africa", "Madagascar"};
	
	public static final int[][] ADJACENT = {
			{4, 1, 5, 6, 3, 2}, //0 Ontario
			{4, 5, 0}, //1 Quebec
			{4, 0, 3, 8}, //2 NW Territory
			{2, 0, 6, 8}, //3 Alberta
			{14, 1, 0, 2}, //4 GreenLand
			{0, 1, 7, 6}, //5 E United States
			{3, 0, 5, 7}, //6 W United States
			{6, 5, 32}, //7 Central America
			{2, 3, 22}, // 8 Alaska
			{14, 15, 13, 10}, // 9 Great Britain
            {9, 13, 11, 37}, // 10 W Europe
            {13, 12, 18, 39, 37, 10}, // 11 S Europe
            {20, 16, 18, 11, 13, 15}, // 12 Ukraine
            {15, 12, 11, 10, 9}, // 13 N Europe
            {15, 9, 4}, // 14 Iceland
            {9, 12, 13, 14}, // 15 Scandinavia
            {20, 27, 17, 18, 12}, // 16 Afghanistan 
            {16, 27, 23, 18}, // 17 India
            {12, 16, 17, 40, 39, 11}, // 18 Middle East
            {26, 22}, //19 Japan 
            {25, 27, 16, 12}, // 20 Ural 
            {22, 24, 25}, // 21 Yakutsk 
            {8, 19, 26, 24, 21}, // 22 Kamchatka 
            {27, 31, 17}, // 23 Siam
            {21, 22, 26, 25}, // 24 Irkutsk 
            {21, 24, 26, 27, 20}, // 25 Siberia 
            {24, 22, 19, 27, 25}, // 26 Mongolia
            {26, 23, 17, 16, 20, 25}, // 27 China 
            {29, 30}, // 28 E Australia  
            {28, 30, 31}, // 29 New Guinea
            {29, 28, 31}, // 30 W Australia 
            {23, 29, 30}, // 31 Indonesia
            {7, 34, 33}, // 32 Venezuela
            {32, 34, 35}, // 33 Peru 
            {32, 37, 35, 33}, // 34 Brazil 
            {33, 34}, // 35 Argentina
            {37, 40, 38}, // 36 Congo 
            {10, 11, 39, 40, 36, 34}, // 37 N Africa 
            {36, 40, 41}, // 38 S Africa 
            {11, 18, 40, 37}, // 39 Egypt 
            {39, 18, 41, 38, 36, 37}, // 40 E Africa 
            {38, 40}}; // 41 Madacascar 
	
	 public static final int[] CONTINENT_IDS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
	 
	 public static final int NUM_CONTINENTS = 6;
	 public static final String[] CONTINENT_NAMES = {"N America","Europe","Asia","Australia","S America","Africa"};
	 public static final int[] CONTINENT_VALUES = {5,5,7,2,2,3};
}
