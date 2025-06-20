package Obsolete;

import java.awt.*;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkedListColor implements Iterable<Color> {
    private class Node {
        double z;
        double w;
        Color color;
        Node next;
        Node previous;
        private Node(double z, double w, Color color, Node next, Node previous) {
            this.z = z;
            this.w = w;
            this.color = color;
            this.next = next;
            this.previous = previous;
        }

        public boolean hasPrio(double z, double w) {
//            if (Math.abs(this.z) < Math.abs(z)) {
//                return true;
//            } else if (Math.abs(this.w) > Math.abs(w)) {
//                return false;
//            } else {
//                return Math.abs(this.z) <= Math.abs(z);
//            }
            return Math.sqrt(this.z * this.z + this.w * this.w) <= Math.sqrt(z * z + w * w);
        }
        public boolean sameDirection(double z, double w) {
            return this.z / this.w == z / w;
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
    public void add(double z, double w, Color color) {
        if (isEmpty()) {
            head = new Node(z, w, color, null, null);
            tail = head;
            size = 1;
        } else {
            Node currentNode = head;
            boolean notSameDirection = true;
            while (currentNode != null) {
                if (currentNode.sameDirection(z, w)) {
                    notSameDirection = false;
                    break;
                }
                currentNode = currentNode.next;
            }
            if (notSameDirection) {
                tail.next = new Node(z, w, color, tail, null);
                tail = tail.next;
                size++;
            }
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
