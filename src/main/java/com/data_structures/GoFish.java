package com.data_structures;

import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;

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


    //TODO tests
            //TODO methods being methody and returning things


    static Random rnd = new Random();

    static int numberOfCardsInHand = 7;

    static int cardsNeededForBook = 4;
    static int totalBooks = 13;

    static String[] suits = {"H", "C", "S", "D"};

    static ArrayList<String> deck;

    static int humanBooks = 0;
    static int computerBooks = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        createDeck();

        //Create hands from deck
        ArrayList<String> humanHand = dealHand();
        ArrayList<String> computerHand = dealHand();

        System.out.println(deck);

        play(humanHand, computerHand);

    }

    public static void play(ArrayList<String> humanHand, ArrayList<String> computerHand) {

        while (!allBooksCreated()) {

            System.out.println("\n** Human's turn **\n");

            humanBooks += playTurn(humanHand, computerHand, true);

            System.out.println("\n** Computer's turn ** \n");

            computerBooks += playTurn(computerHand, humanHand, false);

        }

        if (humanBooks > computerBooks) {
            System.out.println("Human wins!");         //TODO print specific books for each player
        } else {
            System.out.println("Computer wins!");
        }

    }


    public static int playTurn(ArrayList<String> playerHand, ArrayList<String> opponentHand, boolean askForUserInput) {

        System.out.println("Player's hand is ");
        printCards(playerHand);

        System.out.println("Opponent's hand is ");
        printCards(opponentHand);

        //If computer has no cards,

        /*from  http://www.bicyclecards.com/how-to-play/go-fish/
        During the game, if a player is left without cards, he may (when it's his turn to play),
        draw from the stock and then ask for cards of that rank. If there are no cards left in the
        stock, he is out of the game */

        String cardValRequested;

        int booksMadeThisTurn = 0;

        int cards = countCards(playerHand);

        if (cards == 0) {
            System.out.println("Computer has no cards. Drawing from pool");
            String cardDrawnFromPool = drawCardForHand(playerHand);
            cardValRequested = cardDrawnFromPool.substring(0, 1);
            System.out.println("Computer drew a " + cardValRequested);

        }
        else {
            if (askForUserInput) {
                System.out.println("What card value do you want to ask for?");
                cardValRequested = scanner.nextLine().toUpperCase();
                while (handDoesNotHaveCard(cardValRequested, playerHand)) {
                //Validation and also prevents cheating. Can only request a value that the human has in hand
                    System.out.println("You need to have a " + cardValRequested + " already to request more. Try again");
                    cardValRequested = scanner.nextLine().toUpperCase();
                }
            } else {
                cardValRequested = selectCardToAskFor(playerHand);
            }
        }

        System.out.println("Player asks opponent for " + cardValRequested);

        boolean fish = handDoesNotHaveCard(cardValRequested, opponentHand);

        if (fish) {

            System.out.println("Opponent doesn't have that card. Player goes fishing");
            String cardDrawnFromPool = drawCardForHand(playerHand);

            booksMadeThisTurn += makeBooks(playerHand);

            System.out.println("Player got a " + cardDrawnFromPool);
            while (cardDrawnFromPool.equals(cardValRequested)) {
                cardDrawnFromPool = drawCardForHand(playerHand);
                System.out.println("That's the card you wanted - player gets to draw another card, and gets a " + cardDrawnFromPool);
                booksMadeThisTurn += makeBooks(playerHand);

            }
        }
        else {

            System.out.println("Opponent has at least one of the cards requested ");
            transferAllCards(cardValRequested, opponentHand, playerHand);
            System.out.println("Player's hand is ");
            printCards(playerHand);
            System.out.println("Opponent's hand is ");
            printCards(opponentHand);

            booksMadeThisTurn += makeBooks(playerHand);

        }

        booksMadeThisTurn += makeBooks(playerHand);

        System.out.println("Player has made this many books on this turn: " + booksMadeThisTurn);
        System.out.println("humanBooks = " + humanBooks);
        System.out.println("computerBooks = " + computerBooks);

        return booksMadeThisTurn;

    }


    public static void printCards(ArrayList<String> hand) {

        //Sorting, the lazy way - but this doesn't quite sort in the correct order,
        //sorts with numbers first, then A J K Q T

        Collections.sort(hand);
        System.out.println(hand);

    }


    public static int makeBooks(ArrayList<String> hand) {

        //Find books, return total number of books made to book count

        //Brute force approach! Is there a book of Aces? Is there a book of 2s? ...

        int totalBooksMade = 0;

        for (int val = 1 ; val <= totalBooks ; val++) {

            int cardsForBook = 0;

            String value = convertNumberToCardVal(val);

            for (String card : hand) {
                if (card.startsWith(value)) {
                    cardsForBook++;
                }
            }

            if (cardsForBook == cardsNeededForBook) {
                //There's a book of val. Remove all of these cards from hand.
                System.out.println("Made a book of " + val);
                totalBooksMade++;
                removeAll(value, hand);

            }
        }

        return totalBooksMade;
    }

    public static void removeAll(String value, ArrayList<String> hand) {

        //Make a copy of the hand
        //loop over copy, removing cards of that value from hand
        //Modifying a list while looping over it, causes problems, because the
        // length of the list changes, and so the loop condition changes.

        ArrayList<String> copy = new ArrayList<String>(hand);

        for (String card : copy) {
            if (card.startsWith(value)) {
                hand.remove(card);
            }
        }

    }


    public static String drawCardForHand(ArrayList<String> hand) {

        String cardFromDeck = dealCard();
        hand.add(cardFromDeck);
        return cardFromDeck;


    }


    //Transfer all cards of specified value from fromHand to toHand
    public static void transferAllCards(String cardVal, ArrayList<String> fromHand, ArrayList<String> toHand) {

        //Copy cards from fromHand to toHand
        for (String card : fromHand) {
            if (card.startsWith(cardVal)) {
                toHand.add(card);
            }
        }

        //And then remove of that value from fromHand
        removeAll(cardVal, fromHand);

    }


    public static boolean handDoesNotHaveCard(String cardValRequested, ArrayList<String> hand) {

        //search hand for this card. Return true if the card is *not* in the hand.

        boolean cardInHand = false;

        for (String card : hand) {
            if (card.startsWith(cardValRequested)) {
                cardInHand = true;
            }
        }

        return !cardInHand;

    }

    public static String selectCardToAskFor(ArrayList<String> hand) {

        //Randomly pick a value from cards held by computer
        //Todo more intelligent suggestion

        int cardSelectedIndex = rnd.nextInt(hand.size());
        String cardSelected = hand.get(cardSelectedIndex);
        return cardSelected.substring(0,1);

    }


    public static int countCards(ArrayList<String> hand) {
        return hand.size();
    }


    public static boolean allBooksCreated() {

        int booksMade = humanBooks + computerBooks;
        return (booksMade == totalBooks);

    }


    public static ArrayList<String> dealHand() {

        ArrayList<String> hand = new ArrayList<String>();    //max number of cards, rest will be null

        for (int c = 0 ; c < numberOfCardsInHand ; c++) {
            hand.add(dealCard());
        }

        System.out.println("Your hand is " + hand);

        return hand;

    }


    public static String dealCard()  {
        return deck.remove(0);   //return the first card in the deck.
    }


    public static void createDeck() {

        deck = new ArrayList<String>();

        for (int suit = 0 ; suit < suits.length ; suit++) {

            for (int val = 1 ; val <= totalBooks ; val++) {
                deck.add(convertNumberToCardVal(val) + suits[suit]);
            }
        }

        Collections.shuffle(deck);
    }


    public static String convertNumberToCardVal(int val) {

        if (val == 1) {
            return "A";
        }
        else if (val == 10) {
            return "T";
        }
        else if (val == 11) {
            return "J";
        }
        else if (val == 12) {
            return "Q";
        }
        else if (val == 13) {
            return "K";
        }
        else {
            return Integer.toString(val);
        }
    }
}

