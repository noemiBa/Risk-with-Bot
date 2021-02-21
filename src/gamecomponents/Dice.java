package gamecomponents;

import java.util.Arrays;

import player.ActivePlayer;
import player.Player;

import java.util.Random;

public class Dice
{
    //To check who start allocating and attacking
/*

    public void attack(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {

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

    public void attackTwoDefendOne(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[1];
        int[] defenseDices = new int[1];

//        attackDices[0] = roll();
//        defenseDices[0] = roll();

        if (attackDices[0] > defenseDices[0]) {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
            conquerCountry(attackPlayer, defendCountry);
        } else {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
        }
    }

    public void attackTwoDefendTwo(Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[1];
        int[] defenseDices = new int[2];

//        attackDices[0] = roll();
//
//        defenseDices[0] = roll();
//        defenseDices[1] = roll();
        Arrays.sort(defenseDices);

        if (attackDices[0] > defenseDices[1]) {
            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 1);
        } else {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
        }
    }

    public void attackThreeDefendOne(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[2];
        int[] defenseDices = new int[1];

//        attackDices[0] = roll();
//        attackDices[1] = roll();
        Arrays.sort(attackDices);

//        defenseDices[0] = roll();

        if (attackDices[1] > defenseDices[0]) {
            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 1);
            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }
        } else {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
        }
    }

    public void attackThreeDefendTwo(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[2];
        int[] defenseDices = new int[2];

//        attackDices[0] = roll();
//        attackDices[1] = roll();

        Arrays.sort(attackDices);

//        defenseDices[0] = roll();
//        defenseDices[1] = roll();

        Arrays.sort(defenseDices);

        if (attackDices[1] > defenseDices[1] && attackDices[0] > defenseDices[0]) {

            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 2);

            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }

        } else if (attackDices[1] <= defenseDices[1] && attackDices[0] > defenseDices[0]
                || attackDices[0] <= defenseDices[0] && attackDices[1] > defenseDices[1]) {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 1);
        } else {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 2);
        }
    }

    public void attackMoreThanThreeDefendOne(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[3];
        int[] defenseDices = new int[1];

//        attackDices[0] = roll();
//        attackDices[1] = roll();
//        attackDices[2] = roll();

        Arrays.sort(attackDices);

//        defenseDices[0] = roll();

        if (attackDices[2] > defenseDices[0]) {
            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 1);
            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }
        } else {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
        }
    }

    public void attackMoreThanThreeDefendTwo(ActivePlayer attackPlayer, Country attackCountry, Country defendCountry) {
        int[] attackDices = new int[3];
        int[] defenseDices = new int[2];

//        attackDices[0] = roll();
//        attackDices[1] = roll();
//        attackDices[2] = roll();

        Arrays.sort(attackDices);

//        defenseDices[0] = roll();
//        defenseDices[1] = roll();

        Arrays.sort(defenseDices);

        if (attackDices[2] > defenseDices[1] && attackDices[1] > attackDices[0]) {

            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 2);

            if (isCountryEmpty(defendCountry)) {
                attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
                conquerCountry(attackPlayer, defendCountry);
            }

        } else if (attackDices[2] > defenseDices[1] && attackDices[1] <= defenseDices[0] ||
                attackDices[2] <= defenseDices[1] && attackDices[1] > defenseDices[0]) {

            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 1);
            defendCountry.setNumberOfUnits(defendCountry.getNumberOfUnits() - 1);

        } else {
            attackCountry.setNumberOfUnits(attackCountry.getNumberOfUnits() - 2);
        }
    }

    public boolean isCountryEmpty(Country country) {
        return country.getNumberOfUnits() == 0;
    }

    public void conquerCountry(ActivePlayer attackPlayer, Country defendCountry) {
        defendCountry.setControlledBy(attackPlayer);
        defendCountry.setNumberOfUnits(1);
    }
*/}
