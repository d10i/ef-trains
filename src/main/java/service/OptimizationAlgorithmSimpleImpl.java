package service;

import dao.JourneyDao;
import entity.Journey;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
public class OptimizationAlgorithmSimpleImpl implements OptimizationAlgorithm {

    @AutoWired
    private JourneyDao journeyDao;

    private float[][] weights;

    @java.lang.Override
    public List<List<Journey>> performOptimization(int startStation, int endStation) {
        return null;

        //journey.find()
        
    }


}
