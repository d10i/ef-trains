package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Vertex implements Comparable<Vertex> {

    @Autowired
    private JourneyService journeyService;

    public final int id;

    public List<Edge> adjacencies;

    public double minDistance = Double.POSITIVE_INFINITY;

    public Vertex previous;

    public Vertex(int _id, JourneyService _journeyService) {
        id = _id;
        adjacencies = new ArrayList<Edge>();
        journeyService = _journeyService;
    }

    public String toString() {
        return Integer.toString(id);
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    public List<Edge> getAdjacencies(List<Vertex> vertices)
    {
        if(adjacencies.size() != 0) return adjacencies;

        List<Journey> journeys = journeyService.findFrom(id, null);

        for(int i =0; i < journeys.size(); i++)
        {
            Journey journey = journeys.get(i);
            adjacencies.add(new Edge(vertices.get(journey.getArrivalStation()), journey.getPrice()));
        }

        return adjacencies;
    }
}

