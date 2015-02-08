package com.joinef.eftrains.service;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Edge {

    public final Vertex target;

    public final int weight;

    public Edge(Vertex argTarget, int argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}
