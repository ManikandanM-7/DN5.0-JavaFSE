package com.cognizant.dsa.search;

// exercise 7 - financial forecasting using recursion
// formula: futureValue = presentValue * (1 + rate)^n
public class FinancialForecasting {

    // recursive approach
    // base case: when periods = 0 just return the present value
    public static double futureValueRecursive(double pv, double rate, int periods) {
        if (periods == 0) {
            return pv;
        }
        return futureValueRecursive(pv, rate, periods - 1) * (1 + rate);
    }

    // iterative - same result but no stack overflow risk for large n
    public static double futureValueIterative(double pv, double rate, int periods) {
        double val = pv;
        for (int i = 0; i < periods; i++) {
            val = val * (1 + rate);
        }
        return val;
    }

    public static void main(String[] args) {
        double investment = 100000.0;
        double rate = 0.08; // 8% annual

        System.out.println("Initial: Rs." + investment);
        System.out.println("Rate: 8%");
        System.out.println();

        for (int yr = 1; yr <= 10; yr++) {
            double fv = futureValueRecursive(investment, rate, yr);
            System.out.printf("Year %2d -> Rs.%.2f%n", yr, fv);
        }

        // compare recursive vs iterative
        System.out.println();
        double r = futureValueRecursive(investment, rate, 10);
        double it = futureValueIterative(investment, rate, 10);
        System.out.println("Recursive : " + r);
        System.out.println("Iterative : " + it);
        // both should give same result
    }
}
