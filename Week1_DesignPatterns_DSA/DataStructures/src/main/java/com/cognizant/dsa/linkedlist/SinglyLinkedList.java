package com.cognizant.dsa.linkedlist;

/**
 * DSA - Singly Linked List
 * Operations: insert at head/tail, delete by value, search, traverse, reverse
 */
public class SinglyLinkedList {

    // Node
    static class Node {
        int  data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    // Insert at head — O(1)
    public void insertAtHead(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head         = newNode;
    }

    // Insert at tail — O(n)
    public void insertAtTail(int data) {
        Node newNode = new Node(data);
        if (head == null) { head = newNode; return; }
        Node current = head;
        while (current.next != null) current = current.next;
        current.next = newNode;
    }

    // Delete by value — O(n)
    public boolean delete(int data) {
        if (head == null) return false;
        if (head.data == data) { head = head.next; return true; }
        Node current = head;
        while (current.next != null) {
            if (current.next.data == data) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Search — O(n)
    public boolean search(int data) {
        Node current = head;
        while (current != null) {
            if (current.data == data) return true;
            current = current.next;
        }
        return false;
    }

    // Traverse / Print — O(n)
    public void display() {
        Node current = head;
        StringBuilder sb = new StringBuilder("HEAD → ");
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(" → ");
            current = current.next;
        }
        sb.append(" → NULL");
        System.out.println(sb);
    }

    // Reverse — O(n)
    public void reverse() {
        Node prev = null, current = head, next;
        while (current != null) {
            next         = current.next;
            current.next = prev;
            prev         = current;
            current      = next;
        }
        head = prev;
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.insertAtTail(10);
        list.insertAtTail(20);
        list.insertAtTail(30);
        list.insertAtHead(5);
        System.out.print("After inserts: "); list.display();

        System.out.println("Search 20: " + list.search(20));
        list.delete(20);
        System.out.print("After delete 20: "); list.display();

        list.reverse();
        System.out.print("After reverse:   "); list.display();
    }
}
