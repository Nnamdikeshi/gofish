package com.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by admin on 9/30/16.
 */
public class GoFish {

    /** Go Fish with data structures
     *
     * Rules, from Wikipedia:
     *
     * Five cards are dealt from a standard 52-card deck to each player, or seven
     * cards if there are four or fewer players. The remaining cards are shared
     * between the players, usually spread out in a disorderly pile referred to as
     * the "ocean" or "pool".
     *
     * The player whose turn it is to play asks another player for his or her cards
     * of a particular face value. For example Alice may ask, "Bob, do you have any
     * threes?" Alice must have at least one card of the rank she requested.
     * Bob must hand over all cards of that rank if possible. If he has none, Bob
     * tells Alice to "go fish" (or simply "fish"), and Alice draws a card from the
     * pool and places it in her own hand. Then it is the next player's turn – unless
     * the card Alice drew is the card she asked for, in which case she shows it to
     * the other players, and she gets another turn. When any player at any time has
     * all four cards of one face value, it forms a book, and the cards must be placed
     * face up in front of that player.
     *
     * Play proceeds to the left. When all sets of cards have been laid down in books,
     * the game ends. The player with the most books wins.*/

    //TODO tests. More than 2 players - create the required number in this class, and then
            //change the implementation of the selectOpponent method in the Player classes


    static int initialHandSize = 7;

    static Deck deck;

    static ArrayList<Player> players;

    public static void main(String[] args) {

        Log.debugging = true;   //Change to false for real game

        deck = new Deck();

        Log.print(deck.toString());

        HumanPlayer human = new HumanPlayer("Human");    //TODO ask user for their name
        human.setHand(deck.dealHand(initialHandSize));

        ComputerPlayer computer = new ComputerPlayer("Computer");
        computer.setHand(deck.dealHand(initialHandSize));

        players = new ArrayList<Player>();

        players.add(human);
        players.add(computer);

        play(players);

    }

    public static void play(ArrayList<Player> players) {

        int totalPlayers = players.size();
        int playersTurn = 0;

        //TODO multiplayer!

        while (!allBooksCreated()) {

            Player player = players.get(playersTurn);

            Player opponent = player.selectOpponent(players);

            System.out.println("\n** " + player.name + "'s turn **\n");

            player.playTurnAgainst(opponent);

            playersTurn = (playersTurn+1) % totalPlayers;


            Log.print("Size of pool = " + deck.cardsLeft());

        }

        //Who has the most books?

        int maxBooks = 0;

        for (Player player : players) {
            if (player.totalBooksMade > maxBooks) {
                maxBooks = player.totalBooksMade;
            }
        }

        for (Player player : players) {
            if (player.totalBooksMade == maxBooks) {
                System.out.println("Player " + player.name + " is a winner with " + player.totalBooksMade + "!");
            }
        }

    }

    protected static boolean allBooksCreated() {

        int totalBooksMade = 0;
        for (Player player : players) {
            totalBooksMade += player.totalBooksMade;
        }

        return totalBooksMade == Deck.TOTAL_BOOKS;

    }


}

