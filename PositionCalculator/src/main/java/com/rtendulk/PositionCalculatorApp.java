package com.rtendulk;

import java.util.Objects;

public class PositionCalculatorApp {

    public static void main( String[] args )
    {

        if (Objects.isNull(args) || args.length < 2) {
            System.out.println("Invalid input arguments provided");
        } else {
            PositionCalculator positionCalculator = new PositionCalculator();
            try {
                positionCalculator.generateTransactionPositions(args[0],args[1]);
            } catch (Exception e) {
                System.out.println("Exception while processing input files " + e);
            }
        }
    }

}
