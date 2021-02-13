package gamecomponents;

import java.util.Arrays;

import player.ActivePlayer;
import player.Player;

import java.util.Random;

public class Dice {

    private static int throwDice() {
        Random r = new Random();
        return r.nextInt((6 - 1) + 1) + 1;
    }

    //To check who start allocating and attacking
    public static Player whoStart(Player firstPlayer, Player secondPlayer) {
        int diceFirstPlayer = throwDice();
        int diceSecondPlayer = throwDice();

        if (diceFirstPlayer == diceSecondPlayer) {
            do {
                diceFirstPlayer = throwDice();
                diceSecondPlayer = throwDice();
            } while (diceFirstPlayer != diceSecondPlayer);
        }

        if (diceFirstPlayer > diceSecondPlayer) {
            return firstPlayer;
        } else {
            return secondPlayer;
        }
    }

    public static void attack(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {

        int armiesAttack = attackCountry.getNumberOfUnits();
        int armiesDefend = defendCountry.getNumberOfUnits();

        switch (armiesAttack) {
            case 1:
                throw new IllegalArgumentException("Cannot attack");
            case 2:
                if (armiesDefend == 1) {
                    attackTwoDefendOne(attackPlayer, attackCountry, defendCountry);
                }
                if (armiesDefend > 1) {
                    //Will only remove a troop, never conquer
                    attackTwoDefendTwo(attackCountry, defendCountry);
                }
                break;
            case 3:
                if (armiesDefend == 1) {
                    attackThreeDefendOne(attackPlayer, attackCountry, defendCountry);
                }
                if (armiesDefend > 1) {
                    attackThreeDefendTwo(attackPlayer, attackCountry, defendCountry);
                }
                break;
            default:
                //Higher than 3
                if (armiesDefend == 1) {
                    attackMoreThanThreeDefendOne(attackPlayer, attackCountry, defendCountry);
                }
                if (armiesDefend > 1) {
                    attackMoreThanThreeDefendTwo(attackPlayer, attackCountry, defendCountry);
                }
                break;
        }
    }

    public static void attackTwoDefendOne(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[1];
        int[] defenseDices = new int[1];

        attackDices[0] = throwDice();
        defenseDices[0] = throwDice();

        if (attackDices[0] > defenseDices[0]) {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
            conquerCountry(attackPlayer, defendCountry);
        } else {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
        }
    }

    public static void attackTwoDefendTwo(Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[1];
        int[] defenseDices = new int[2];

        attackDices[0] = throwDice();

        defenseDices[0] = throwDice();
        defenseDices[1] = throwDice();
        Arrays.sort(defenseDices);

        if (attackDices[0] > defenseDices[1]) {
            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 1);
        } else {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
        }
    }

    public static void attackThreeDefendOne(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[2];
        int[] defenseDices = new int[1];

        attackDices[0] = throwDice();
        attackDices[1] = throwDice();
        Arrays.sort(attackDices);

        defenseDices[0] = throwDice();

        if (attackDices[1] > defenseDices[0]) {
            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 1);
            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }
        } else {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
        }
    }

    public static void attackThreeDefendTwo(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[2];
        int[] defenseDices = new int[2];

        attackDices[0] = throwDice();
        attackDices[1] = throwDice();

        Arrays.sort(attackDices);

        defenseDices[0] = throwDice();
        defenseDices[1] = throwDice();

        Arrays.sort(defenseDices);

        if (attackDices[1] > defenseDices[1] && attackDices[0] > defenseDices[0]) {

            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 2);

            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }

        } else if (attackDices[1] <= defenseDices[1] && attackDices[0] > defenseDices[0]
                || attackDices[0] <= defenseDices[0] && attackDices[1] > defenseDices[1]) {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 1);
        } else {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 2);
        }
    }

    public static void attackMoreThanThreeDefendOne(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[3];
        int[] defenseDices = new int[1];

        attackDices[0] = throwDice();
        attackDices[1] = throwDice();
        attackDices[2] = throwDice();

        Arrays.sort(attackDices);

        defenseDices[0] = throwDice();

        if (attackDices[2] > defenseDices[0]) {
            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 1);
            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }
        } else {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
        }
    }

    public static void attackMoreThanThreeDefendTwo(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[3];
        int[] defenseDices = new int[2];

        attackDices[0] = throwDice();
        attackDices[1] = throwDice();
        attackDices[2] = throwDice();

        Arrays.sort(attackDices);

        defenseDices[0] = throwDice();
        defenseDices[1] = throwDice();

        Arrays.sort(defenseDices);

        if (attackDices[2] > defenseDices[1] && attackDices[1] > attackDices[0]) {

            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 2);

            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }

        } else if (attackDices[2] > defenseDices[1] && attackDices[1] <= defenseDices[0] ||
                attackDices[2] <= defenseDices[1] && attackDices[1] > defenseDices[0]) {

            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 1);
            defendCountry.setNumberOfInfantry(defendCountry.getNumberOfInfantry() - 1);

        } else {
            attackCountry.setNumberOfInfantry(attackCountry.getNumberOfInfantry() - 2);
        }
    }

    public static boolean isCountryEmpty(Country country) {
        return country.getNumberOfUnits() == 0;
    }

    public static void conquerCountry(ActivePlayer attackPlayer, Country defendCountry) {
        defendCountry.setControlledBy(attackPlayer);
        defendCountry.setNumberOfInfantry(1);
    }
}