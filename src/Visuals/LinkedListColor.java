package Visuals;

import java.awt.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkedListColor implements Iterable<Color> {
    private class Node {
        double z;
        Color color;
        Node next;
        Node previous;
        private Node(double z, Color color, Node next, Node previous) {
            this.z = z;
            this.color = color;
            this.next = next;
            this.previous = previous;
        }
    }
    // smallest
    private Node head;
    // biggest
    private Node tail;
    private int size;
    public LinkedListColor() {
        size = 0;
    }
    public LinkedListColor(double z, Color color) {
        super();
        add(z, color);
    }
    public void add(double z, Color color) {
        if (isEmpty()) {
            head = new Node(z, color, null, null);
            tail = head;
            size++;
            return;
        } else if (z <= head.z) {
            if (color.getAlpha() == 255) {
                head = new Node(z, color, null, null);
                size = 1;
            } else {
                head = new Node(z, color, head, null);
                head.next.previous = head;
                size++;
            }
            return;
        }
        int numCurrentNode = 1;
        Node currentNode = head;
        while (currentNode.next != null) {
            if (z <= currentNode.next.z) {
                if (color.getAlpha() == 255) {
                    currentNode.next = new Node(z, color, null, currentNode);
                    tail = currentNode.next;
                    size = numCurrentNode + 1;
                } else {
                    currentNode.next = new Node(z, color, currentNode.next, currentNode);
                    currentNode.next.next.previous = currentNode.next;
                    size++;
                }
                return;
            }
            currentNode = currentNode.next;
            numCurrentNode++;
        }
        if (currentNode.color.getAlpha() != 255) {
            currentNode.next = new Node(z, color, null, currentNode);
            tail = currentNode.next;
            size++;
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    // iterate biggest to smallest
    @Override
    public Iterator<Color> iterator() {
        return new Iterator<>() {
            private Node currentNode = tail;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public Color next() {
                Color currentColor = currentNode.color;
                currentNode = currentNode.previous;
                return currentColor;
            }
        };
    }
    @Override
    public void forEach(Consumer<? super Color> action) {
        Iterable.super.forEach(action);
    }
    @Override
    public Spliterator<Color> spliterator() {
        return Iterable.super.spliterator();
    }
    public void print() {
        Node currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.color);
            currentNode = currentNode.next;
        }
    }
}
