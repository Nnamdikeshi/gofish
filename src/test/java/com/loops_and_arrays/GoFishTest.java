package com.loops_and_arrays;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertArrayEquals;

public class GoFishTest extends TestCase {

    //Spell setUp like this and it runs automatically, without the @Before annotation
    //Also test cases should start with testBlahBlah...

    public void setUp() {
        GoFish.rnd = new Random();
    }

    public void testGame() throws Exception {

        //Play a whole game with a smaller deck that only has 5 values. If code crashes, test fails.

        String[] deck = { "AH" , "AD", "AC", "AS" ,
                "2H" , "2D", "2C", "2S" ,
                "3H" , "3D", "3C", "3S",
                "4H" , "4D", "4C", "4S",
                "5H" , "5D", "5C", "5S" };

        GoFish.deck = deck;
        GoFish.totalSuits = 5;
        GoFish.totalCardsInDeck = 20;

        GoFish.humanHand = GoFish.dealHand();
        GoFish.computerHand = GoFish.dealHand();

        //Some whatever input. Should carry us through a whole game
        String input = "A\n2\n3\n4\n67\n5\nA\n2\n3\n4\n5\n2\n3\n4\n5\nA\n2\n3\n2\n3\n4\n1\n1\n2";

        //Where does the user input come from?
        //Can replace the System.in input stream with an input stream of our creation
        //credit to this Stack Overflow http://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
       // System.setIn(in);

        GoFish.scanner = new Scanner(in);

        GoFish.play();

    }
 
    public void testMakeBooks() throws Exception {

        //Should have book of Aces
        String[] exampleHand =      {"AH",  "4H", "AD", "7D", "AS", null, "6H", null, "JC", null, "AC", "6C"};
        String[] exampleHandAfter = { null, "4H", null, "7D", null, null, "6H", null, "JC", null, null, "6C"};

        int booksMade = GoFish.makeBooks(exampleHand);

        assertEquals(1, booksMade);

        assertArrayEquals(exampleHand, exampleHandAfter);



    }


    public void testTransferAllCards() throws Exception {

        String[] humanHandBefore = {"AH", "4H", "6H", "JC", null, "6C"};
        String[] humanHandAfter = {"AH", "4H", null, "JC", null, null};

        String[] computerHandBefore = {"5H", "4C", null, null, null, "JS"};
        String[] computerHandAfter = {"5H", "4C", "6H", "6C", null, "JS"};

        String transferVal = "6";


        GoFish.transferAllCards(transferVal, humanHandBefore, computerHandBefore);

        assertArrayEquals(humanHandAfter, humanHandBefore);
        assertArrayEquals(computerHandAfter, computerHandBefore);

    }

    public void testTransferCard() throws Exception {


        String[] humanHandBefore = {"AH", "4H", "6H", "JC", null, "6C"};
        String[] humanHandAfter = {"AH", "4H", null, "JC", null, "6C"};

        String[] computerHandBefore = {"5H", "4C", null, null, null, "JS"};
        String[] computerHandAfter = {"5H", "4C", "6H", null, null, "JS"};

        String transferVal = "6H";


        GoFish.transferCard(transferVal, humanHandBefore, computerHandBefore);

        assertArrayEquals(humanHandAfter, humanHandBefore);
        assertArrayEquals(computerHandAfter, computerHandBefore);


    }

    public void testDoesHandHaveCard() throws Exception {

        String[] exampleHand = {"AH", "4H", "6H", "JC", null, "6C"};

        assertTrue(GoFish.doesHandHaveCard("AH", exampleHand));
        assertTrue(GoFish.doesHandHaveCard("6C", exampleHand));

        assertFalse(GoFish.doesHandHaveCard("JD", exampleHand));
        assertFalse(GoFish.doesHandHaveCard("7S", exampleHand));

    }

    public void testSelectCardToAskFor() throws Exception {

        String[] exampleHand = {"AH", "4H", "6H", "JC", null, "6C"};

        String[] okReturnValues = {"4", "A", "6", "J"};

        //Value should be one of: A, 4, 6, J

        String value = GoFish.selectCardToAskFor(exampleHand);

        boolean valueInOkValues = false;
        for (int x = 0 ; x < okReturnValues.length ; x++) {
            if (okReturnValues[x].equals(value)) {
                //ok!
                valueInOkValues = true;
            }
        }

        assertTrue(valueInOkValues);

    }

    public void testAllBooksCreated() throws Exception {

        GoFish.humanBooks = 6;
        GoFish.computerBooks = 7;

        assertTrue(GoFish.allBooksCreated());

        GoFish.humanBooks = 4;
        GoFish.computerBooks = 2;

        assertFalse(GoFish.allBooksCreated());


    }

    public void testJustCards() throws Exception {
        String[] exampleHand = {"AH", "4H", "6H", null, "JC", null, "6C"};
        String[] exampleHandNoNulls = {"AH", "4H", "6H", "JC", "6C"};

        assertArrayEquals(exampleHandNoNulls, GoFish.justCards(exampleHand));


    }

    public void testCountCards() throws Exception {

        String[] exampleHand = {"AH", "4H", "6H", null, "JC", null, "6C"};
        assertEquals(5, GoFish.countCards(exampleHand));

    }

    public void testDealHand() throws Exception {
        //todo
    }

    public void testDrawCardForHand() throws Exception {
        //todo
    }


    public void testDealCard() throws Exception {
        //todo
    }

    public void testCreateDeck() throws Exception {
        //todo
    }

    public void testComputerPlay() throws Exception {
        //todo
    }

    public void testHumanPlay() throws Exception {
        //todo
    }

    public void testPrintCards() throws Exception {
        //todo
    }


}