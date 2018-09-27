package com.rtendulk;

import com.rtendulk.domain.PositionDomain;
import com.rtendulk.domain.TransactionDomain;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class PositionCalculatorIT
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PositionCalculatorIT( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PositionCalculatorIT.class );
    }

    public void testPositionCalculatorObjectCreation()
    {
        PositionCalculator positionCalculator = new PositionCalculator();
        Assert.assertNotNull(positionCalculator);
    }

    public void testCalculatePositionsInvalidInputs()
    {

        PositionCalculator positionCalculator = new PositionCalculator();
        Assert.assertNotNull(positionCalculator);

        /**
         * Null/Empty Input Parameters
         */
        try {
            positionCalculator.generateTransactionPositions(null,null);
            Assert.fail("Not Expected Success");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }

        /**
         * Invalid File Path Provided
         */
        try {
            positionCalculator.generateTransactionPositions("/invalid/transactionPath/transactionFiles.txt",
                    "/invalid/positionPath/positionFiles.txt");
            Assert.fail("Not Expected Success");

        } catch (IOException e) {
            //Expected Exception
        }
        catch (Exception e) {

            Assert.fail("Not Expected Exception");
        }
    }

    public void testCalculatePositionsValidFilePath() {
        PositionCalculator positionCalculator = new PositionCalculator();
        Assert.assertNotNull(positionCalculator);

        try {
            positionCalculator.generateTransactionPositions("D:\\Rajanikant\\Workspace\\PositionCalculator\\src\\test\\resources\\transactions.txt",
                    "D:\\Rajanikant\\Workspace\\PositionCalculator\\src\\test\\resources\\positions.txt");

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Not Expected IOException");
        }
        catch (Exception e) {

            e.printStackTrace();
            Assert.fail("Not Expected Exception");
        }

    }

//    public void testCalculateEndOfDayPositions() {
//        PositionCalculator positionCalculator = new PositionCalculator();
//        Assert.assertNotNull(positionCalculator);
//
//        try {
//            List<PositionDomain> endOfTheDayPositions = positionCalculator.generateEndOfDayPositions(null,null);
//            Assert.fail("Not Expected Success");
//
//        } catch (IllegalArgumentException e) {
//
//        }
//        catch (Exception e) {
//            Assert.fail("Not Expected Success");
//        }
//
//        try {
//            List<PositionDomain> endOfTheDayPositions = positionCalculator.generateEndOfDayPositions(getStartOfTheDayPositionsData(),getInputTransactions());
//
//            Assert.assertNotNull(endOfTheDayPositions);
//            Assert.assertEquals(2, endOfTheDayPositions.size());
//
//            for(PositionDomain positionDomain:endOfTheDayPositions) {
//                if("E".equalsIgnoreCase(positionDomain.getAccountType())) {
//                    Assert.assertEquals(Integer.valueOf(-1100),positionDomain.getQuantity());
//                } else if ("I".equalsIgnoreCase(positionDomain.getAccountType())) {
//                Assert.assertEquals(Integer.valueOf(1100),positionDomain.getQuantity());
//            }
//
//            }
//        }  catch (Exception e) {
//            Assert.fail("Not Expected Exception");
//        }
//    }

    private List<PositionDomain> getStartOfTheDayPositionsData () {
        List<PositionDomain> startOfTheDayPositions = new ArrayList<>();

        /**
         * APPL,101,E,10000
         * APPL,201,I,-10000
         */
        PositionDomain positionDomain = new PositionDomain();
        positionDomain.setInstrument("APPL");
        positionDomain.setAccount("101");
        positionDomain.setAccountType("E");
        positionDomain.setQuantity(10000);

        startOfTheDayPositions.add(positionDomain);

        PositionDomain positionDomain1 = new PositionDomain();
        positionDomain1.setInstrument("APPL");
        positionDomain1.setAccount("201");
        positionDomain1.setAccountType("I");
        positionDomain1.setQuantity(-10000);

        startOfTheDayPositions.add(positionDomain1);


        return startOfTheDayPositions;
    }

    private List<TransactionDomain> getInputTransactions () {
        List<TransactionDomain> inputTransactions = new ArrayList<>();

        /**
         *  {
         *       "TransactionId": 2,
         *       "Instrument": "APPL",
         *       "TransactionType": "S",
         *       "TransactionQuantity": 200
         *    },
         *
         *   {
         *       "TransactionId": 5,
         *       "Instrument": "APPL",
         *       "TransactionType": "B",
         *       "TransactionQuantity": 100
         *    },
         *    {
         *       "TransactionId": 6,
         *       "Instrument": "APPL",
         *       "TransactionType": "S",
         *       "TransactionQuantity": 20000
         *    },
         *    {
         *       "TransactionId": 10,
         *       "Instrument": "APPL",
         *       "TransactionType": "B",
         *       "TransactionQuantity": 9000
         *    }
         */

        TransactionDomain transactionDomain1 = new TransactionDomain();
        transactionDomain1.setTransactionId("2");
        transactionDomain1.setInstrument("APPL");
        transactionDomain1.setTransactionType("S");
        transactionDomain1.setTransactionQuantity(200);

        inputTransactions.add(transactionDomain1);

        TransactionDomain transactionDomain2 = new TransactionDomain();
        transactionDomain2.setTransactionId("5");
        transactionDomain2.setInstrument("APPL");
        transactionDomain2.setTransactionType("B");
        transactionDomain2.setTransactionQuantity(100);

        inputTransactions.add(transactionDomain2);

        TransactionDomain transactionDomain3 = new TransactionDomain();
        transactionDomain3.setTransactionId("6");
        transactionDomain3.setInstrument("APPL");
        transactionDomain3.setTransactionType("S");
        transactionDomain3.setTransactionQuantity(20000);

        inputTransactions.add(transactionDomain3);

        TransactionDomain transactionDomain4 = new TransactionDomain();
        transactionDomain4.setTransactionId("10");
        transactionDomain4.setInstrument("APPL");
        transactionDomain4.setTransactionType("B");
        transactionDomain4.setTransactionQuantity(9000);

        inputTransactions.add(transactionDomain4);

        return inputTransactions;
    }
}
