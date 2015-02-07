package com.joinef.eftrains.service;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Vertex implements Comparable<Vertex> {

    public final int id;

    public Edge[] adjacencies;

    public double minDistance = Double.POSITIVE_INFINITY;

    public Vertex previous;

    public Vertex(int _id) {
        id = _id;
    }

    public String toString() {
        return Integer.toString(id);
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
