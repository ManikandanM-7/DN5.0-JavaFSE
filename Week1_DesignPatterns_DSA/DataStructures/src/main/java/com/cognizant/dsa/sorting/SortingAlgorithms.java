package com.cognizant.dsa.sorting;

import java.util.Arrays;

/**
 * DSA - Sorting Algorithms
 * Each with time/space complexity analysis
 */
public class SortingAlgorithms {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp    = arr[j];
                    arr[j]      = arr[j + 1];
                    arr[j + 1]  = temp;
                    swapped     = true;
                }
            }
            if (!swapped) break; // Already sorted — optimization
        }
    }
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j   = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i     = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
            }
        }
        int temp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = temp;
        return i + 1;
    }
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left,      L, 0, n1);
        System.arraycopy(arr, mid + 1,   R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static void main(String[] args) {
        int[] original = {64, 34, 25, 12, 22, 11, 90, 45, 78, 3};

        System.out.println("Original: " + Arrays.toString(original));

        int[] a1 = original.clone();
        bubbleSort(a1);
        System.out.println("Bubble:   " + Arrays.toString(a1));

        int[] a2 = original.clone();
        insertionSort(a2);
        System.out.println("Insertion:" + Arrays.toString(a2));

        int[] a3 = original.clone();
        quickSort(a3, 0, a3.length - 1);
        System.out.println("Quick:    " + Arrays.toString(a3));

        int[] a4 = original.clone();
        mergeSort(a4, 0, a4.length - 1);
        System.out.println("Merge:    " + Arrays.toString(a4));
    }
}
