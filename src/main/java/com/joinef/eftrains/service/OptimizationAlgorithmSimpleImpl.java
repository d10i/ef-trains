package com.joinef.eftrains.service;

import com.joinef.eftrains.dao.StationDao;
import com.joinef.eftrains.entity.Journey;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
public class OptimizationAlgorithmSimpleImpl implements OptimizationAlgorithm {

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private StationDao stationDao;

    @Override
    public List<List<Journey>> performOptimization(int startStation, int endStation) {

        List<String> stationKeys = stationDao.findAllKeys();

        HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();

        for (String stationKey : stationKeys) {
            Vertex vertex = new Vertex(stationKey, journeyService);
            vertices.put(stationKey, vertex);
        }

        Vertex startVertex = vertices.get(startStation);
        Vertex endVertex = vertices.get(endStation);
        List<Journey> resultPrice = RunDjikstra(startVertex, endVertex, vertices, OptimizationType.Price);

        List<List<Journey>> output = new ArrayList<List<Journey>>();
        output.add(resultPrice);

        List<Journey> resultDuration = RunDjikstra(startVertex, endVertex, vertices, OptimizationType.Duration);
        output.add(resultDuration);

        return output;
    }

    private List<Journey> RunDjikstra(Vertex startVertex, Vertex endVertex, HashMap<String, Vertex> vertices, OptimizationType optimizationType)
    {
        Dijkstra.computePaths(startVertex, vertices, optimizationType);
        List<Vertex> finalPath = Dijkstra.getShortestPathTo(endVertex);
        finalPath.add(endVertex);

        List<Journey> result = new ArrayList<Journey>();

        int pathSize = finalPath.size();
        for(int i =0; i < pathSize - 1; i++)
        {
            String stationOne = finalPath.get(i).id;
            String stationTwo = finalPath.get(i + 1).id;

            result.add(journeyService.findFrom(stationOne, null).get(stationTwo));
        }

        return result;
    }

    public void setJourneyService(JourneyService journeyService) {
        this.journeyService = journeyService;
    }
}
