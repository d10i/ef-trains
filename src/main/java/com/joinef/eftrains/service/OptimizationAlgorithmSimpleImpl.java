package com.joinef.eftrains.service;

import com.joinef.eftrains.dao.JourneyDao;
import com.joinef.eftrains.entity.Journey;

import java.util.List;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
public class OptimizationAlgorithmSimpleImpl implements OptimizationAlgorithm {

    //@AutoWired
    private JourneyDao journeyDao;

    private float[][] weights;

    @Override
    public List<List<Journey>> performOptimization(int startStation, int endStation) {
        return null;
        //for(int i =0; i < )
        
    }


}
