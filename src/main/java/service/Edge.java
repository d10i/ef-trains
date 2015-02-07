package service;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Edge {

    public final Vertex target;

    public final double weight;

    public Edge(Vertex argTarget, double argWeight)
    {
        target = argTarget; weight = argWeight;
    }
}
