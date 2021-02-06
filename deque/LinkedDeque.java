package deque;

import javax.swing.*;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node sentFront;
    private Node sentBack;

    /** An empty LinkedDeque. */
    public LinkedDeque() {
        sentFront = new Node(null, sentBack);
        sentBack = new Node(sentFront, null);
        sentFront.next = sentBack;
        size = 0;

    }

    private class Node {
        private T value;
        private Node prev;
        private Node next;

        Node(Node prev, Node next){
            // this.value = value;
            this.prev = prev;
            this.next = next;
        }

        Node(Node prev, T value, Node next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }

    public void addFirst(T item) {
        Node node = new Node(sentFront, item, sentFront.next);
        sentFront.next = node;
        node.next.prev = node;
        size += 1;
    }

    public void addLast(T item) {
        Node node = new Node(sentBack.prev, item, sentBack);
        sentBack.prev = node;
        node.prev.next = node;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = sentFront.next.value;
        sentFront.next = sentFront.next.next;
        sentFront.next.prev = sentFront;
        size -= 1;
        return temp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = sentBack.prev.value;
        sentBack.prev = sentBack.prev.prev;
        sentBack.prev.next = sentBack;
        size -= 1;
        return temp;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node temp = sentFront.next;
        for (int i = 0; i < index; i++){
            temp = temp.next;
        }
        return temp.value;
    }

    public int size() {
        return size;
    }
}
