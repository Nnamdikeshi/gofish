package com.objects;

import java.util.ArrayList;

public abstract class Player {

    Hand hand;
    String name;
    int totalBooksMade;

    public Player(String name) {
        this.name = name;
        totalBooksMade = 0;

    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }


    public int playTurnAgainst(Player opponent) {

        System.out.println(this + "'s hand is " + hand);

        Log.print(opponent + "'s hand is " + opponent.hand);

        //If computer has no cards,

        /*from  http://www.bicyclecards.com/how-to-play/go-fish/
        During the game, if a player is left without cards, he may (when it's his turn to play),
        draw from the stock and then ask for cards of that rank. If there are no cards left in the
        stock, he is out of the game */

        String cardValRequested;

        if (hand.size() == 0) {

            System.out.println(this + " has no cards. Drawing from pool");
            Card cardDrawnFromPool = drawCardForHand();
            cardValRequested = cardDrawnFromPool.value;
            System.out.println(this + " drew the " + cardValRequested);

        }
        else {
            cardValRequested = identifyCardValueToAskFor();
        }

        System.out.println("Player asks opponent for " + cardValRequested);

        boolean fish = !opponent.hasCard(cardValRequested);

        if (fish) {

            System.out.println(opponent + " doesn't have that card. Player goes fishing");
            Card cardDrawnFromPool = drawCardForHand();

            System.out.println("Player got a " + cardDrawnFromPool);
            while (cardDrawnFromPool.value.equals(cardValRequested)) {
                cardDrawnFromPool = drawCardForHand();
                System.out.println("That's the card you wanted - player gets to draw another card, and gets a " + cardDrawnFromPool);
            }
        }

        else {

            System.out.println(opponent + " has at least one of the cards requested ");

            ArrayList<Card> opponentCards = opponent.hand.removeAll(cardValRequested);
            hand.addAll(opponentCards);

            System.out.println(this + "'s hand is " + hand);
            Log.print(opponent + "'s hand is " + opponent.hand);

        }

        int booksMadeThisTurn = makeBooks();
        totalBooksMade += booksMadeThisTurn;

        System.out.println("Player has made this many books on this turn: " + booksMadeThisTurn);
        System.out.println("humanBooks = " + totalBooksMade);

        return booksMadeThisTurn;

    }

    protected int makeBooks() {
        //Find books, return total number of books made to book count


        int totalBooksMade = 0;

        //Brute force approach! Is there a book of Aces? Is there a book of 2s? ...
        for (String value : Deck.values) {

            int cardsForBook = hand.countCardsOfValue(value);

            if (cardsForBook == Deck.TOTAL_SUITS) {
                //There's a book of val. Remove all of these cards from hand.
                System.out.println("Made a book of " + value);
                totalBooksMade++;
                hand.removeAll(value);

            }
        }

        return totalBooksMade;

    }



    private boolean hasCard(String cardVal) {
        return this.hand.hasCardOfThisValue(cardVal);
    }

    private Card drawCardForHand() {

        return GoFish.deck.dealCard();

    }


    protected abstract String identifyCardValueToAskFor() ;

    protected abstract Player selectOpponent(ArrayList<Player> players);

    @Override
    public String toString() {
        return this.name;
    }
}
