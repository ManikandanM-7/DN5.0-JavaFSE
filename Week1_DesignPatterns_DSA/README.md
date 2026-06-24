# Week 1 - Design Patterns and DSA

## design patterns

**exercise 1 - singleton**
- Logger class with private constructor and synchronized getInstance()
- tested that both variables point to same instance

**exercise 2 - factory method**
- ShapeFactory creates Circle, Rectangle, Triangle based on string input
- client code doesnt need to know which class is instantiated

**additional patterns done**
- Builder - Computer class with required and optional fields
- Adapter - PayPalAdapter wrapping third party SDK
- Observer - StockMarket notifying multiple investors
- Strategy - DataSorter switching between BubbleSort and QuickSort

## DSA

**exercise 2 - ecommerce search**
- linear search by product name - O(n)
- binary search by product id - O(log n), array must be sorted first
- tested with sample products

**exercise 7 - financial forecasting**
- recursive approach: futureValue(pv, rate, n-1) * (1+rate)
- iterative approach: same result but safer for large n
- compared both approaches

## how to run
```bash
cd DesignPatterns
mvn clean compile exec:java -Dexec.mainClass="com.cognizant.designpatterns.DesignPatternsMain"

cd DataStructures
mvn clean compile exec:java -Dexec.mainClass="com.cognizant.dsa.search.EcommerceSearch"
```
