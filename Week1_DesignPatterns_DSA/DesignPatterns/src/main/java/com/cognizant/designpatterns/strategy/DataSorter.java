package com.cognizant.designpatterns.strategy;

import java.util.Arrays;

// strategy pattern - swap sorting algorithm at runtime without changing DataSorter
// useful when you want to pick best algorithm based on data size

interface SortStrategy {
    void sort(int[] data);
    String getName();
}

class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int tmp = data[j]; data[j] = data[j+1]; data[j+1] = tmp;
                }
            }
        }
    }
    @Override public String getName() { return "BubbleSort"; }
}

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
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (data[j] <= pivot) {
                i++;
                int tmp = data[i]; data[i] = data[j]; data[j] = tmp;
            }
        }
        int tmp = data[i+1]; data[i+1] = data[high]; data[high] = tmp;
        return i + 1;
    }

    @Override public String getName() { return "QuickSort"; }
}

public class DataSorter {

    private SortStrategy strategy;

    public DataSorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] data) {
        System.out.println("using: " + strategy.getName());
        System.out.println("before: " + Arrays.toString(data));
        strategy.sort(data);
        System.out.println("after:  " + Arrays.toString(data));
    }

    public static void main(String[] args) {
        int[] data1 = {64, 34, 25, 12, 22, 11, 90};
        int[] data2 = {90, 12, 45, 67, 23, 78, 34, 56};

        DataSorter sorter = new DataSorter(new BubbleSortStrategy());
        sorter.sort(data1);

        System.out.println();

        // switch strategy at runtime
        sorter.setStrategy(new QuickSortStrategy());
        sorter.sort(data2);
    }
}
