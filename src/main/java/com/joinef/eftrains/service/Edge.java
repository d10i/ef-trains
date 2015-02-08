package com.joinef.eftrains.service;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Edge {

    public final Vertex target;

    public final float weight;

    public Edge(Vertex argTarget, float argWeight) {
        target = argTarget;
        weight = argWeight;
    }
}
