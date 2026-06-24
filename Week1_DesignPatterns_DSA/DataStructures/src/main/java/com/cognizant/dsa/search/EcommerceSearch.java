package com.cognizant.dsa.search;

import java.util.Arrays;
import java.util.Comparator;

// exercise 2 - search function for ecommerce platform
// tried both linear and binary search
public class EcommerceSearch {

    static class Product {
        int productId;
        String productName;
        String category;
        double price;

        public Product(int productId, String productName, String category, double price) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{id=" + productId + ", name=" + productName + ", price=" + price + "}";
        }
    }

    // linear search - goes through each element one by one
    // O(n) time complexity
    public static Product linearSearchByName(Product[] products, String name) {
        for (Product p : products) {
            if (p.productName.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // binary search - array must be sorted first
    // O(log n) time complexity - much faster for large datasets
    public static Product binarySearchById(Product[] sorted, int targetId) {
        int low = 0;
        int high = sorted.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (sorted[mid].productId == targetId) {
                return sorted[mid];
            } else if (sorted[mid].productId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Product[] products = {
            new Product(105, "Samsung Galaxy S24", "Electronics", 74999.00),
            new Product(203, "Nike Air Max", "Footwear", 12999.00),
            new Product(101, "Apple MacBook Air", "Electronics", 114999.00),
            new Product(312, "Levis 501 Jeans", "Clothing", 3999.00),
            new Product(150, "Sony WH-1000XM5", "Electronics", 29999.00),
        };

        // linear search test
        Product found = linearSearchByName(products, "Nike Air Max");
        System.out.println("Linear search result: " + found);

        // sort by id then binary search
        Arrays.sort(products, Comparator.comparingInt(p -> p.productId));
        Product result = binarySearchById(products, 150);
        System.out.println("Binary search result: " + result);

        // not found case
        System.out.println("Not found: " + binarySearchById(products, 999));
    }
}
