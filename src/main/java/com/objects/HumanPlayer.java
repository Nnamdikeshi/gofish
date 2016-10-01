package com.objects;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

    static Scanner scanner;

    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    @Override
    protected String identifyCardValueToAskFor() {
        System.out.println("What card value do you want to ask for?");

        String cardValRequested = scanner.nextLine().toUpperCase();
        while (!hand.hasCardOfThisValue(cardValRequested)) {
                  //Validation and also prevents cheating. Can only request a value that the human has in hand
            System.out.println("You need to have a " + cardValRequested + " already to request more. Try again");
            cardValRequested = scanner.nextLine().toUpperCase();
        }

        return cardValRequested;
    }

    @Override
    protected Player selectOpponent(ArrayList<Player> players) {

        for (Player p : players) {
            if (!p.equals(this)) {
                return p;              //return first other player. TODO ask user for who to ask for cards from
            }
        }

        return null;
    }
}
