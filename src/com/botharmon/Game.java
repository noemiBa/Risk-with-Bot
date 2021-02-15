
package com.botharmon;

import player.*;
import ui.*;
import gamecomponents.Deck;
import gamecomponents.Dice;
import gamecomponents.Turns;

/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Game
{
    private Map map;
    private ActivePlayer[] activePlayers;
    private PassivePlayer[] passivePlayers;
    private Deck deck;
    private Dice dice;
    private Window window;
    private Turns turns;

    public static void main(String[] args)
    {
        Game risk = new Game();
        risk.map = new Map();
        risk.activePlayers = ActivePlayer.initialiseActivePlayers();
        risk.passivePlayers = PassivePlayer.initialisePassivePlayers();
        Player.assignCountriesControlled(risk.activePlayers, risk.passivePlayers, risk.map);
        risk.deck = new Deck();
        risk.dice = new Dice();
        risk.window = new Window(risk);
        risk.turns = new Turns();
        risk.turns.nextTurns(risk);
    }

    public Map getMap()
    {
        return map;
    }
    public ActivePlayer[] getActivePlayers()
    {
        return activePlayers;
    }
    public PassivePlayer[] getPassivePlayers()
    {
        return passivePlayers;
    }
    public Deck getDeck()
    {
        return deck;
    }
    public Window getWindow()
    {
        return window;
    }
}
