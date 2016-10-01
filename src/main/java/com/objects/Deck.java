package com.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by admin on 9/30/16.
 */
public class Deck {

    ArrayList<Card> cards;

    final static String[] suits = {"H", "C", "S", "D"};

    final static String[] values = { Card.ACE, "2", "3", "4", "5", "6", "7", "8", "9", Card.JACK, Card.QUEEN, Card.KING };

    static ArrayList<String> valuesList = new ArrayList<String>(Arrays.asList(values));   //used by the Card compareTo method

    final static int TOTAL_BOOKS = values.length;    //13 books possible
    final static int TOTAL_SUITS = suits.length;     //And 4 different suits.


    public Deck() {

            cards = new ArrayList<Card>();

            for (int suit = 0 ; suit < suits.length ; suit++) {

                for (String value : values) {
                    cards.add(new Card(value, suits[suit]));
                }
            }

            Collections.shuffle(cards);
        }

    public Card dealCard() {
        return cards.remove(0);
    }


    public Hand dealHand(int size) {

        Hand hand = new Hand();

        for (int x = 0 ; x < size ; x++) {
            hand.addCard(dealCard());
        }

        return hand;
    }
}
