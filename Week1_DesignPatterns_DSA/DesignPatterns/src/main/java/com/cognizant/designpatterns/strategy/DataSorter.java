package com.cognizant.designpatterns.strategy;

import java.util.Arrays;

/**
 * Strategy Pattern (Behavioral)
 * Scenario: A data processor that can sort data using different algorithms
 * (BubbleSort, QuickSort) depending on dataset size — swappable at runtime.
 */

// Strategy interface
interface SortStrategy {
    void sort(int[] data);
    String getName();
}

// Concrete Strategy 1 — Bubble Sort
class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int temp    = data[j];
                    data[j]     = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}

// Concrete Strategy 2 — Quick Sort
class QuickSortStrategy implements SortStrategy {

    @Override
    public void sort(int[] data) {
        quickSort(data, 0, data.length - 1);
    }

    private void quickSort(int[] data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);
            quickSort(data, low, pi - 1);
            quickSort(data, pi + 1, high);
        }
    }

    private int partition(int[] data, int low, int high) {
        int pivot = data[high];
        int i     = low - 1;
        for (int j = low; j < high; j++) {
            if (data[j] <= pivot) {
                i++;
                int temp = data[i];
                data[i]  = data[j];
                data[j]  = temp;
            }
        }
        int temp        = data[i + 1];
        data[i + 1]     = data[high];
        data[high]      = temp;
        return i + 1;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}

// Context — DataSorter uses whatever strategy is set
public class DataSorter {

    private SortStrategy strategy;

    public DataSorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    // Strategy can be swapped at runtime
    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] data) {
        System.out.println("Sorting using: " + strategy.getName());
        System.out.println("Before: " + Arrays.toString(data));
        strategy.sort(data);
        System.out.println("After:  " + Arrays.toString(data));
    }

    public static void main(String[] args) {
        int[] smallData = {64, 34, 25, 12, 22, 11, 90};
        int[] largeData = {90, 12, 45, 67, 23, 78, 34, 56, 89, 1};

        DataSorter sorter = new DataSorter(new BubbleSortStrategy());
        sorter.sort(smallData);

        System.out.println();

        // Swap strategy at runtime
        sorter.setStrategy(new QuickSortStrategy());
        sorter.sort(largeData);
    }
}
