package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
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
        adjacencies = new ArrayList<>();
        journeyService = _journeyService;
    }

    public String toString() {
        return id;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

    public List<Edge> getAdjacencies(Map<String, Vertex> vertices, OptimizationType typeOfOptimization) {
        //if (adjacencies.size() != 0) return adjacencies;

        HashMap<String, Journey> journeys = journeyService.findFrom(id, null);

        for (Map.Entry<String, Journey> journeyEntry : journeys.entrySet()) {
            switch (typeOfOptimization) {
                case Price:
                adjacencies.add(new Edge(vertices.get(journeyEntry.getKey()), journeyEntry.getValue().getPrice()));
                break;

                case Duration:
                    adjacencies.add(new Edge(vertices.get(journeyEntry.getKey()), (float)journeyEntry.getValue().getDuration()));
                    break;
            }
        }

        return adjacencies;
    }
}

