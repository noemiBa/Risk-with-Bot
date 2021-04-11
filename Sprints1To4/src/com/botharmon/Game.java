
package com.botharmon;

import gamecomponents.Deck;
import gamecomponents.Turns;
import player.ActivePlayer;
import player.PassivePlayer;
import player.Player;
import ui.Map;
import ui.Window;

/*
 * @Author: BotHarmon (Jess Dempsey, Rebeca Buarque, Noemi Banal)
 */
public class Game
{
    private Map map;
    private ActivePlayer[] activePlayers;
    private PassivePlayer[] passivePlayers;
    private Deck deck;
    private Window window;
    private Turns turns;
    private static Game risk;

    public static void main(String[] args)
    {
        risk = new Game();
        risk.map = new Map();
        risk.activePlayers = ActivePlayer.initialiseActivePlayers();
        risk.passivePlayers = PassivePlayer.initialisePassivePlayers();
        Player.assignCountriesControlled(risk.activePlayers, risk.passivePlayers, risk.map);
        risk.deck = new Deck();
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

    public Turns getTurns()
    {
        return turns;
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    public void setActivePlayers(ActivePlayer[] activePlayers)
    {
        this.activePlayers = activePlayers;
    }

    public void setPassivePlayers(PassivePlayer[] passivePlayers)
    {
        this.passivePlayers = passivePlayers;
    }


    public void setDeck(Deck deck)
    {
        this.deck = deck;
    }

    public void setWindow(Window window)
    {
        this.window = window;
    }

    public void setTurns(Turns turns)
    {
        this.turns = turns;
    }
}
