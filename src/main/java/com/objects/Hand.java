package com.objects;


import java.util.ArrayList;
import java.util.Collections;

public class Hand {

    ArrayList<Card> cards;

    Hand() {
        cards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);
        sort();
    }

    public int size() {
        return cards.size();
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public void addAll(ArrayList<Card> cardsToAdd) {

        for (Card card : cardsToAdd)  {
            cards.add(card);
        }

    }


    public ArrayList<Card> removeAll(String value) {

        ArrayList<Card> removedCards = new ArrayList<Card>();

        //For looping
        ArrayList<Card> temp = new ArrayList<Card>(cards);
        for (Card c : temp) {
            if (c.value.equals(value)) {
                cards.remove(c);
                removedCards.add(c);
            }
        }

        return removedCards;
    }


    private void sort() {
        Collections.sort(cards);

    }

    public boolean hasCardOfThisValue(String cardValRequested) {

        for (Card c : cards) {

            if (c.value.equals(cardValRequested)) {
                return true;
            }
        }

        return false;
    }


    public int countCardsOfValue(String value) {

        int count = 0;

        for (Card c : cards ) {
            if (c.value.equals(value)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString(){
        sort();
        return cards.toString();
    }



}
