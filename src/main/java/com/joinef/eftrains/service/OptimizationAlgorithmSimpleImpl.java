package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
public class OptimizationAlgorithmSimpleImpl implements OptimizationAlgorithm {

    @Autowired
    private JourneyService journeyService;

    @Override
    public List<List<Journey>> performOptimization(int startStation, int endStation) {

        //populateWeights();
        int stationCount = journeyService.countStations();

        List<Vertex> vertices = new ArrayList<Vertex>();

        for(int i =0; i < stationCount; i++) {
            Vertex vertex = new Vertex(i, journeyService);
            vertices.add(vertex);
        }

        /* for(int i =0; i < stationCount; i++)
        {
            List<Edge> edges = new ArrayList<Edge>();

            for(int j =0; j < stationCount; j++)
            {
                if(!Float.isNaN(weights[i][j]))
                {
                    Vertex targetVertex = vertices.get(j);
                    //edges.add(new Edge(targetVertex, weights[i][j]));
                }
            }

            vertices.get(i).adjacencies = edges;
        }
        */

        Vertex startVertex = vertices.get(startStation);
        Vertex endVertex = vertices.get(endStation);

        Dijkstra.computePaths(startVertex, vertices);
        List<Vertex> finalPath = Dijkstra.getShortestPathTo(endVertex);

        List<List<Journey>> output = new ArrayList<List<Journey>>();
        List<Journey> result = new ArrayList<Journey>();
        output.add(result);

        int pathSize = finalPath.size();
        for(int i =0; i < pathSize - 1; i++)
        {
            int stationOne = finalPath.get(i).id;
            int stationTwo = finalPath.get(i + 1).id;

            result.add(new Journey(journeyService.find(stationOne, stationTwo, null), stationOne, stationTwo, null, null));
        }

        result.add(new Journey(journeyService.find(pathSize-1,endStation, null), finalPath.get(pathSize - 1).id, endStation, null, null));

        return output;
    }

    /*
    private void populateWeights()
    {
        int stationCount = journeyService.countStations();
        weights = new float[stationCount][];

        for(int i =0; i < stationCount; i++)
        {
            weights[i] = new float[stationCount];

            for(int j =0; j < stationCount; j++)
            {
                weights[i][j] = journeyService.find(i, j, null);
            }
        }
    }
    */

    public void setJourneyService(JourneyService journeyService) {
        this.journeyService = journeyService;
    }
}
