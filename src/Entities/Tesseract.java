package Entities;

import Data.Dimention;

import java.util.ArrayList;
import java.util.Arrays;

public class Tesseract extends Mesh {
    public Tesseract(Dimention start, int sideLength) {
        super();

        // generate all vertices
        ArrayList<Dimention> dimentions = new ArrayList<>();
        for (double x : new double[]{start.x(), start.x() + sideLength}) {
            for (double y : new double[]{start.y(), start.y() + sideLength}) {
                for (double z : new double[]{start.z(), start.z() + sideLength}) {
                    for (double w : new double[]{start.w(), start.w() + sideLength}) {
                        dimentions.add(new Dimention(x, y, z, w));
                    }
                }
            }
        }

        // generate all edges
//        ArrayList<Edge> edges = new ArrayList<>();
//        for (Dimention one : dimentions) {
//            for (Dimention two : dimentions) {
//                if (threeCoordsMatching(one, two)) {
//                    boolean alreadyHasEdge = false;
//                    for (Edge edge : edges) {
//                        if (edge.containsTheseDimentions(one, two)) {
//                            alreadyHasEdge = true;
//                            break;
//                        }
//                    }
//                    if (!alreadyHasEdge) {
//                        edges.add(new Edge(one, two));
//                    }
//                }
//            }
//        }

        // generate all squares
        ArrayList<Square> squares = new ArrayList<>();
        for (int i = 0; i < dimentions.size(); i++) {
            for (int j = i + 1; j < dimentions.size(); j++) {
                for (int k = j + 1; k < dimentions.size(); k++) {
                    for (int l = k + 1; l < dimentions.size(); l++) {
                        Square square = createWithTesting(dimentions.get(i), dimentions.get(j), dimentions.get(k), dimentions.get(l));
                        if (square != null) {
                            squares.add(square);
                        }
                    }
                }
            }
        }

        for (Square square : squares) {
//            if (square.dimentions[0].w() == 0 && square.dimentions[1].w() == 500) {
                addRectangle(square.dimentions[1], square.dimentions[0], square.dimentions[2]);
//                System.out.println(square);
//            }
        }
    }

    private class Edge {
        private Dimention one;
        private Dimention two;
        private Edge(Dimention one, Dimention two) {
            this.one = one;
            this.two = two;
        }

        private boolean containsTheseDimentions(Dimention one, Dimention two) {
            return this.one == one && this.two == two || this.two == one && this.one == two;
        }
    }
    private class Square {
        private Dimention[] dimentions;
        private Square(Dimention one, Dimention two, Dimention three, Dimention four) {
            dimentions = new Dimention[]{one, two, three, four};
        }

        private boolean sameSquare(Square square) {
            int matching = 0;
            for (Dimention one : dimentions) {
                for (Dimention two : square.dimentions) {
                    if (one.equals(two)) {
                        matching++;
                    }
                }
            }
            return matching == 4;
        }
        public String toString() {
            return Arrays.toString(dimentions);
        }
    }
    private Square createWithTesting(Dimention... dimentions) {
        if (dimentions.length != 4) {
            return null;
        }
        int edges = 0;
        for (int i = 0; i < dimentions.length; i++) {
            for (int j = i + 1; j < dimentions.length; j++) {
                if (threeCoordsMatching(dimentions[i], dimentions[j])) {
                    edges++;
                }
            }
        }

        if (edges != 4) {
            return null;
        }

        boolean[] dimentionUsed = new boolean[4];
        Dimention[] orderedDimentions = new Dimention[4];
        orderedDimentions[0] = dimentions[0];
        dimentionUsed[0] = true;

        int numUsed = 1;
        int currentNum = 1;

        while(numUsed < 4) {
            if (!dimentionUsed[currentNum]) {
                if (threeCoordsMatching(orderedDimentions[numUsed - 1], dimentions[currentNum])) {
                    orderedDimentions[numUsed] = dimentions[currentNum];
                    numUsed++;
                    dimentionUsed[currentNum] = true;
                }
            }
            if (currentNum == 3) {
                currentNum = 1;
            } else {
                currentNum++;
            }
        }

        return new Square(orderedDimentions[0], orderedDimentions[1], orderedDimentions[2], orderedDimentions[3]);
    }
    private static boolean threeCoordsMatching(Dimention one, Dimention two) {
        int numSharedAspects = 0;
        if (one.x() == two.x()) {
            numSharedAspects++;
        }
        if (one.y() == two.y()) {
            numSharedAspects++;
        }
        if (one.z() == two.z()) {
            numSharedAspects++;
        }
        if (one.w() == two.w()) {
            numSharedAspects++;
        }
        return numSharedAspects == 3;
    }
}
