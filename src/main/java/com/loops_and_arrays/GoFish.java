package com.loops_and_arrays;

import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;

public class GoFish {

    /** Go Fish with loops, arrays, if-statements, methods
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



    static Random rnd;

    static int numberOfCardsInHand = 7;
    static int totalCardsInDeck = 52;

    static int cardsNeededForBook = 4;
    static int totalSuits = 13;

    static String[] deck;

    static int humanBooks = 0;
    static int computerBooks = 0;

    static String[] humanHand;
    static String[] computerHand;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        rnd = new Random();

        deck = createDeck();

        //Create hands from deck
        String[] humanHand = dealHand();
        String[] computerHand = dealHand();

        System.out.println(Arrays.toString(deck));

        play();

    }

    public static void play() {

        while (true) {

            System.out.println("Human's turn");

            humanPlay();
            if (allBooksCreated()) { break; }

            System.out.println("Computer's turn");

            computerPlay();
            if (allBooksCreated()) { break; }

        }


        if (humanBooks > computerBooks) {
            System.out.println("Human wins!");         //TODO print specific books for each player
        } else {
            System.out.println("Computer wins!");
        }

    }


    public static void computerPlay() {

        System.out.println("Computer's hand is ");
        printCards(computerHand);

        System.out.println("Human's hand is ");
        printCards(humanHand);

        //If computer has no cards,

        /*from  http://www.bicyclecards.com/how-to-play/go-fish/
        During the game, if a player is left without cards, he may (when it's his turn to play),
        draw from the stock and then ask for cards of that rank. If there are no cards left in the
        stock, he is out of the game */

        String cardValRequested;

        int cards = countCards(computerHand);

        if (cards == 0) {
            System.out.println("Computer has no cards. Drawing from pool");
            String cardDrawnFromPool = drawCardForHand(computerHand);
            cardValRequested = cardDrawnFromPool.substring(0, 1);
            System.out.println("Computer drew a " + cardValRequested);

        }
        else {
            cardValRequested = selectCardToAskFor(computerHand);
        }

        System.out.println("Computer asks human for " + cardValRequested);

        boolean fish = doesHandHaveCard(cardValRequested, humanHand);

        if (!fish) {

            System.out.println("Computer goes fishing");

            String cardDrawnFromPool = drawCardForHand(computerHand);

            System.out.println("Computer got a " + cardDrawnFromPool);

            int newBooks = makeBooks(computerHand);
            computerBooks += newBooks;

            while (cardDrawnFromPool.equals(cardValRequested)) {
                cardDrawnFromPool = drawCardForHand(computerHand);
                System.out.println("Computer gets to draw another card, gets a " + cardDrawnFromPool);

                newBooks = makeBooks(computerHand);
                computerBooks += newBooks;

            }
        }
        else {

            System.out.println("Human has at least one of the cards requested ");
            transferAllCards(cardValRequested, humanHand, computerHand);
            System.out.println("Computer's hand is ");
            printCards(computerHand);
            System.out.println("Human's hand is ");
            printCards(humanHand);

            int newBooks = makeBooks(computerHand);
            computerBooks += newBooks;

        }

        int newBooks = makeBooks(computerHand);
        computerBooks += newBooks;
        System.out.println("Computer has this many books " + computerBooks);

    }



    public static void humanPlay() {

        System.out.println("Computer's hand is ");
        printCards(computerHand);

        System.out.println("Human's hand is ");
        printCards(humanHand);


        int cards = countCards(humanHand);


        String cardValRequested;
        if (cards == 0) {
            System.out.println("Human has no cards. Drawing from pool");
            String cardDrawnFromPool = drawCardForHand(humanHand);
            cardValRequested = cardDrawnFromPool.substring(0, 1);
            System.out.println("Human drew a " + cardValRequested);

        }
        else {

            System.out.println("What card value do you want to ask the computer for?");
            cardValRequested = scanner.nextLine().toUpperCase();
            if (!doesHandHaveCard(cardValRequested, humanHand)) {
                //Validation and also prevents cheating. Can only request a value that the human has in hand
                System.out.println("You need to have a " + cardValRequested + " already to request more. Try again");
                cardValRequested = scanner.nextLine().toUpperCase();
            }

        }

        boolean fish = doesHandHaveCard(cardValRequested, computerHand);

        if (!fish) {

            System.out.println("Computer does not have a "  + cardValRequested + " human goes fishing");

            String cardDrawnFromPool = drawCardForHand(humanHand);
            System.out.println("Human drew a " + cardDrawnFromPool);

            int newBooks = makeBooks(humanHand);
            humanBooks += newBooks;

            while (cardDrawnFromPool.equals(cardValRequested)) {
                System.out.println("Human gets another turn");
                cardDrawnFromPool = drawCardForHand(humanHand);
                newBooks = makeBooks(humanHand);
                humanBooks += newBooks;

            }

        }
        else {

            System.out.println("Computer has at least one of that card, transferring");
            transferAllCards(cardValRequested, computerHand, humanHand);
            int newBooks = makeBooks(humanHand);
            humanBooks += newBooks;
        }


        System.out.println("Computer's hand is now ");
        printCards(computerHand);

        System.out.println("Human's hand is now ");
        printCards(humanHand);

        humanBooks += makeBooks(humanHand);

        System.out.println("Human has this many books " + humanBooks);


    }

    public static void printCards(String[] hand) {

        String[] justCards = justCards(hand);

        //Sorting, the lazy way - but this doesn't quite sort in the correct order,
        //sorts with numbers first, then A J K Q T

        ArrayList<String> cardList = new ArrayList<String>(Arrays.asList(justCards));
        Collections.sort(cardList);
        justCards = cardList.toArray(new String[cardList.size()]);

        for (int x = 0 ; x < justCards.length ; x++) {
                System.out.print(justCards[x] + " ");
        }

        System.out.println();
    }


    public static int makeBooks(String[] hand) {

        //Find books, add to

        //Brute force approach! Is there a book of Aces? Is there a book of 2s? ...

        int totalBooksMade = 0;

        for (int val = 1 ; val <= totalSuits ; val++) {

            int cardsForBook = 0;

            String value = val + "";

            if (val == 1) {
                value = "A";
            }
            else if (val == 10) {
                value = "T";  //yuck
            }
            else if (val == 11) {
                value = "J";
            }
            else if (val == 12) {
                value = "Q";
            }
            else if (val == 13) {
                value = "K";
            }

            for (int x = 0 ; x < hand.length ; x++) {
                if (hand[x] != null) {
                    if (hand[x].startsWith(value)) {
                        cardsForBook++;
                    }
                }
            }

            if (cardsForBook == cardsNeededForBook) {
                //There's a book of val

                System.out.println("Made a book of " + val);

                totalBooksMade++;
                for (int x = 0 ; x < hand.length ; x++) {

                    if (hand[x] != null) {

                        if (hand[x].startsWith(value)) {
                            hand[x] = null;
                        }
                    }

                }

            }

        }

        return totalBooksMade;
    }

    public static String drawCardForHand(String[] hand) {

        String fromDeck = dealCard();

        //loop along until next empty slot in hand

        for (int x = 0 ; x < hand.length ; x++) {
            if (hand[x] == null) {
                hand[x] = fromDeck;
                return fromDeck;
            }

        }

        System.out.println("Shouldn't be here");
        return null;  //should not get here

    }


    //Transfer all cards of specified value from fromHand to toHand
    public static void transferAllCards(String cardVal, String[] fromHand, String[] toHand) {

        //Copy cards from humanHand to computerHand

        for (int x = 0; x < fromHand.length; x++) {

            if (fromHand[x] != null) {
                if (fromHand[x].startsWith(cardVal)) {
                    String card = fromHand[x];
                    transferCard(card, fromHand, toHand);
                }
            }

        }
    }

    //Transfer single card from one hand to another
    public static void transferCard(String card, String[] fromHand, String[] toHand) {

        for (int x = 0; x < fromHand.length; x++) {

            if (fromHand[x] != null && fromHand[x].equals(card)) {
                fromHand[x] = null;

                //Find empty location to put card in toHand

                for (int y = 0 ; y < toHand.length ; y++) {

                    if (toHand[y] == null) {
                        toHand[y] = card;
                        return;
                    }
                }

                System.out.println("Error - can't transfer " + card);
            }

        }
    }


    public static boolean doesHandHaveCard(String cardValRequested, String[] hand) {

        //search hand for this card.

        for (int x = 0 ; x < hand.length ; x++) {

            if (hand[x] != null) {
                if (hand[x].startsWith(cardValRequested)) {
                    return true;
                }
            }
        }

        return false;

    }

    public static String selectCardToAskFor(String[] hand) {

        //Randomly pick a value from cards held by computer
        //Todo more intelligent suggestion

        //Copy all values to another array, select one value at random

        int cardsInHand = countCards(hand);

        String[] handJustCards = justCards(hand);

        int cardSelected = rnd.nextInt(cardsInHand);

        return handJustCards[cardSelected].substring(0,1);

    }

    public static String[] justCards(String[] hand) {

        int count = countCards(hand);

        String[] cards = new String[count];

        int i = 0;

        for (int x = 0 ; x < hand.length ; x++) {
            if (hand[x] != null) {
                cards[i] = hand[x];
                i++;
            }
        }

        return cards;

    }

    public static int countCards(String[] hand) {

        int count = 0;
        for (int x = 0 ; x < hand.length ; x++) {
            if (hand[x] != null) {
                count++;
            }
        }
        return count;
    }


    public static boolean allBooksCreated() {

        int totalBooks = humanBooks + computerBooks;

        return (totalBooks == totalSuits);

    }


    public static String[] dealHand() {

        String[] hand = new String[totalCardsInDeck];    //max number of cards, rest will be null

        for (int c = 0 ; c < numberOfCardsInHand ; c++) {
            hand[c] = dealCard();
        }

        System.out.println("Your hand is " + Arrays.toString(hand));

        return hand;

    }


    public static String dealCard()  {

        String[] justCards = justCards(deck);
        int cardPick = rnd.nextInt(justCards.length);
        String cardPickString = justCards[cardPick];

        //Now find in deck and make it null

        for (int x = 0 ; x < deck.length ; x++) {
            if (deck[x] != null && deck[x].equals(cardPickString)) {
                deck[x] = null;
                break;
            }
        }

        return cardPickString;

    }


    public static String[] createDeck() {

        String deck[] = new String[52];

        String[] suits = {"H", "C", "S", "D"};

        int deckCount = 0;

        for (int suit = 0 ; suit < suits.length ; suit++) {

            for (int val = 1 ; val <= totalSuits ; val++) {
                if (val == 1) {
                    deck[deckCount] = "A" + suits[suit];
                }
                else if (val == 10) {
                    deck[deckCount] = "T" + suits[suit];
                }
                else if (val == 11) {
                    deck[deckCount] = "J" + suits[suit];
                }
                else if (val == 12) {
                    deck[deckCount] = "Q" + suits[suit];
                }
                else if (val == 13) {
                    deck[deckCount] = "K" + suits[suit];
                }
                else {
                    deck[deckCount] = val + suits[suit];
                }
                deckCount++;
            }
        }

        System.out.println(Arrays.toString(deck));

        return deck;

    }
}
