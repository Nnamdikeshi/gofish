package com.data_structures;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;

public class GoFishTest extends TestCase {
    

    public void testMakeBooks() throws Exception {

        //Should have book of 3s
        String[] exampleHand =      {"3H",  "4H", "3D", "7D", "3S", "6H", "JC", "3C", "6C"};
        String[] exampleHandAfter = {"4H", "7D", "6H",  "JC", "6C"};

        //Convert array to ArrayList
        ArrayList<String> exampleHandList = new ArrayList<String>(Arrays.asList(exampleHand));

        int booksMade = GoFish.makeBooks(exampleHandList);

        //Convert ArrayList back to array for assert statement
        String[] actualHandAfter = exampleHandList.toArray(new String[exampleHandList.size()]);

        //Should have one book made
        assertEquals(1, booksMade);

        //Assert(expected, actual)
        assertArrayEquals(exampleHandAfter, actualHandAfter);

    }

    public void testDrawCardForHand() throws Exception {

    }


    public void testTransferCards() throws Exception {

        String[] fromHandBefore = {"AH", "4H", "6H", "JC", "6C"};
        ArrayList<String> fromHandList = arrayToList(fromHandBefore);

        String[] fromHandExpectedAfter = {"AH", "4H", "JC"};

        String[] toHandBefore = {"5H", "4C", "JS"};
        ArrayList<String> toHandList = arrayToList(toHandBefore);

        String[] toHandExpectedAfter = {"5H", "4C", "JS", "6H", "6C",};

        String transferVal = "6";

        GoFish.transferAllCards(transferVal, fromHandList, toHandList);

        System.out.println("from " + fromHandList);
        System.out.println("to " + toHandList);

        //Convert back to Arrays for assert

        String[] toHandAfterActual = listToArray(toHandList);
        String[] fromHandAfterActual = listToArray(fromHandList);

        assertArrayEquals(toHandExpectedAfter, toHandAfterActual);
        assertArrayEquals(fromHandExpectedAfter, fromHandAfterActual);


    }


    private ArrayList<String> arrayToList(String[] array) {
        return new ArrayList<String>(Arrays.asList(array));
    }

    private String[] listToArray(ArrayList<String> list) {
        return list.toArray(new String[list.size()]);
    }


    public void testRemoveAll() throws Exception {

        String[] handBefore = {"AH", "4H", "6H", "JC", "6C", "5D"};
        ArrayList<String> fromHandList = arrayToList(handBefore);

        GoFish.removeAll("6", fromHandList);

        String[] handExpected = {"AH", "4H", "JC", "5D"};

        String[] handActual = listToArray(fromHandList);

        assertArrayEquals(handExpected, handActual);




    }

    public void testDoesHandHaveCard() throws Exception {

    }

    public void testSelectCardToAskFor() throws Exception {

    }

    public void testCountCards() throws Exception {

    }

    public void testAllBooksCreated() throws Exception {

    }

    public void testDealHand() throws Exception {

    }

    public void testDealCard() throws Exception {

    }

    public void testCreateDeck() throws Exception {

        //52 cards?
        GoFish.createDeck();
        assertEquals(GoFish.deck.size(), 52);

        //All different?
        Set deckSet = new HashSet(GoFish.deck);
        assertEquals(deckSet.size(), 52);

        //How else to test?

    }

    public void testConvertNumberToCardVal() throws Exception {

        assertEquals(GoFish.convertNumberToCardVal(1), "A");
        assertEquals(GoFish.convertNumberToCardVal(11), "J");
        assertEquals(GoFish.convertNumberToCardVal(12), "Q");
        assertEquals(GoFish.convertNumberToCardVal(13), "K");
        assertEquals(GoFish.convertNumberToCardVal(6), "6");
        assertEquals(GoFish.convertNumberToCardVal(4), "4");

    }
}