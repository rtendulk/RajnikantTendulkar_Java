package com.rtendulk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.rtendulk.domain.PositionDomain;
import com.rtendulk.domain.TransactionDomain;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionCalculator {

    private static final String BUY = "B";
    private static final String SELL = "S";

    private static final String EXTERNAL = "E";
    private static final String INTERNAL = "I";

    private static final String HEADER = "Instrument,Account,AccountType,Quantity,Delta";

    private static final String LINE_SEPERATOR = System.getProperty("line.separator");


    /**
     * Method to
     * @param transactionsFilePath
     * @param positionsFilePath
     * @throws Exception
     */
    public void generateTransactionPositions(final String transactionsFilePath, final String positionsFilePath) throws Exception {

        /**
         * Validate if valid input provided
         */
        if(StringUtils.isNotBlank(transactionsFilePath) && StringUtils.isNotBlank(positionsFilePath)) {

            List<PositionDomain> startOfTheDayPositions = new ArrayList<>();
            List<TransactionDomain> transactionsOfDay = new ArrayList<>();

            /**
             * Process Positions File
             */
            processStartOfDayPositions(positionsFilePath,startOfTheDayPositions);

            /**
             * Calculate End Of Day positions
             */
            List<PositionDomain> endOfDayPositions = generateEndOfDayPositions(startOfTheDayPositions,processTransactions(transactionsFilePath));

            /**
             * Write to file
             */
            writeEndOfDayTransactions(endOfDayPositions);

        } else {
            throw new IllegalArgumentException("Invalid Input Arguments Provided");
        }
    }

    /**
     * Method to convert positions file in objects
     * @param positionsFilePath
     * @param startOfTheDayPositions
     * @throws Exception
     */
    private void processStartOfDayPositions (final String positionsFilePath, List<PositionDomain> startOfTheDayPositions) throws Exception {
        try (Stream<String> positionsStream = Files.lines(Paths.get(positionsFilePath)).skip(1)) {

            List<String> positionsList =  positionsStream.collect(Collectors.toList());

            populateStartOftheDayPositions(startOfTheDayPositions,positionsList);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Method to extract trsnsaction Data
     * @param transactionsFilePath
     * @throws Exception
     */
    private List<TransactionDomain> processTransactions (final String transactionsFilePath) throws Exception {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(transactionsFilePath), new TypeReference<List<TransactionDomain>>(){});
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Exception while processing transactions data", e);
        }
    }

    /**
     * Write EndOfDayTrsnsactions to Expected_EndOfDay_Positions.txt under target folder
     * @param endOfDayPositions
     * @throws Exception
     */
    private void writeEndOfDayTransactions(final List<PositionDomain> endOfDayPositions) throws Exception {

        StringBuilder endOfDayPositionsFileContent = new StringBuilder();

        endOfDayPositionsFileContent.append(HEADER);

        for(PositionDomain positionDomain:endOfDayPositions) {
            endOfDayPositionsFileContent.append(LINE_SEPERATOR).append(positionDomain);
        }

        File file = new File("Expected_EndOfDay_Positions.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(endOfDayPositionsFileContent.toString());
        } finally {
            if (writer != null) writer.close();
        }

    }

    /**
     * Method to populate start of the day positions
     * @param positionDomains
     * @param positionsList
     * @throws Exception
     */
    private void populateStartOftheDayPositions(List<PositionDomain> positionDomains,final List<String> positionsList) throws Exception {
        for(String positionRow : positionsList) {
            List<String> positionAttributes = Arrays.asList(positionRow.split(","));
            try {
                positionDomains.add(new PositionDomain(positionAttributes.get(0),positionAttributes.get(1),positionAttributes.get(2),Integer.valueOf(positionAttributes.get(3))));
            } catch (Exception e) {
                throw new Exception ("Error while processing positions data", e);
            }
        }
    }


    /**
     * Method to get End Of Day Positions
     * @param startOfTheDayPositionsData
     * @param inputTransactions
     * @return
     * @throws Exception
     */
    public List<PositionDomain> generateEndOfDayPositions(List<PositionDomain> startOfTheDayPositionsData, List<TransactionDomain> inputTransactions) throws Exception {

        List<PositionDomain> endOfDayPositions = new ArrayList<>();

        if(Objects.isNull(startOfTheDayPositionsData) && Objects.isNull(inputTransactions)) {
            throw new IllegalArgumentException("Input Data Provided is null");
        } else {

            for (PositionDomain positionDomain:startOfTheDayPositionsData) {

                Integer bulkQuantityBuy = inputTransactions.stream().filter(t -> t.getInstrument().equalsIgnoreCase(positionDomain.getInstrument()) && t.getTransactionType().equalsIgnoreCase(BUY)).mapToInt(TransactionDomain::getTransactionQuantity).sum();
                Integer bulkQuantitySell = inputTransactions.stream().filter(t -> t.getInstrument().equalsIgnoreCase(positionDomain.getInstrument()) && t.getTransactionType().equalsIgnoreCase(SELL)).mapToInt(TransactionDomain::getTransactionQuantity).sum();


                PositionDomain targetPositionDomain = new PositionDomain();
                targetPositionDomain.setAccount(positionDomain.getAccount());
                targetPositionDomain.setAccountType(positionDomain.getAccountType());
                targetPositionDomain.setInstrument(positionDomain.getInstrument());

                /**
                 * First Send Bulk Buy
                 */
                Integer endOfDayBuyQuantity = getQuantity(BUY,positionDomain.getAccountType(),positionDomain.getQuantity(),bulkQuantityBuy);

                /**
                 * Now Send Bulk Sell
                 */
                Integer endOfDayQuantity = getQuantity(SELL,positionDomain.getAccountType(),endOfDayBuyQuantity,bulkQuantitySell);

                targetPositionDomain.setQuantity(endOfDayQuantity);
                targetPositionDomain.setDelta(endOfDayQuantity - positionDomain.getQuantity());

                System.out.println(targetPositionDomain);
                endOfDayPositions.add(targetPositionDomain);
            }
        }

        return endOfDayPositions;
    }

    /**
     * Method to get final End OF Day Quantity
     * @param transactionType
     * @param accountType
     * @param quantity
     * @param totalTransactionQuantity
     * @return
     */
    private Integer getQuantity (final String transactionType,final String accountType, final Integer quantity,final Integer totalTransactionQuantity ) {

        if(StringUtils.isNotBlank(transactionType) && StringUtils.isNotBlank(accountType) && !Objects.isNull(quantity) && !Objects.isNull(totalTransactionQuantity)) {

            if(BUY.equalsIgnoreCase(transactionType)) {

                if(EXTERNAL.equalsIgnoreCase(accountType)) {
                    return quantity+totalTransactionQuantity;
                } else if (INTERNAL.equalsIgnoreCase(accountType)) {
                    return quantity - totalTransactionQuantity;
                }

            } else if (SELL.equalsIgnoreCase(transactionType)) {
                if(EXTERNAL.equalsIgnoreCase(accountType)) {
                    return quantity-totalTransactionQuantity;
                } else if (INTERNAL.equalsIgnoreCase(accountType)) {
                    return quantity + totalTransactionQuantity;
                }
            }
        }

        return Integer.valueOf(0);
    }
}
