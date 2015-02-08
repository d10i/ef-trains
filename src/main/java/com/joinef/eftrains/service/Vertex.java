package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Vertex implements Comparable<Vertex> {

    @Autowired
    private JourneyService journeyService;

    public final String id;

    public List<Edge> adjacencies;

    public double minDistance = Double.POSITIVE_INFINITY;

    public Vertex previous;

    public Vertex(String _id, JourneyService _journeyService) {
        id = _id;
        adjacencies = new ArrayList<Edge>();
        journeyService = _journeyService;
    }

    public String toString() {
        return id;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    public List<Edge> getAdjacencies(Map<String, Vertex> vertices)
    {
        if (adjacencies.size() != 0) return adjacencies;

        Hashtable<Integer, Journey> journeys = journeyService.findFrom(id, null);

        for(int i : journeys.keySet())
        {
            Journey journey = journeys.get(i);
            adjacencies.add(new Edge(vertices.get(journey.getArrivalStation()), journey.getPrice()));
        }

        return adjacencies;
    }
}

