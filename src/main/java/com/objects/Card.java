package com.objects;


public class Card implements Comparable<Card> {

    String value;
    String suit;

    final static String ACE = "A";
    final static String JACK = "J";
    final static String QUEEN = "Q";
    final static String KING = "K";


    public Card(String val, String suit) {
        this.value = val;
        this.suit = suit;
    }

    public Card(int val, String suit) {

        this.value = convertNumberToCardVal(val);
        this.suit = suit;

    }

    public String convertNumberToCardVal(int val) {

        if (val == 1) {
            return ACE;
        }
        else if (val == 11) {
            return JACK;
        }
        else if (val == 12) {
            return QUEEN;
        }
        else if (val == 13) {
            return KING;
        }
        else {
            return Integer.toString(val);
        }
    }


    @Override
    public String toString() {
        return value +  " of " + suit;
    }

    //The sort order for cards has A at the start, followed by JQK.
    @Override
    public int compareTo(Card otherCard) {

        int thisIndex = Deck.valuesList.indexOf(this.value);
        int otherIndex = Deck.valuesList.indexOf(otherCard.value);

        return thisIndex - otherIndex;

    }

}

