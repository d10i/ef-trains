package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;

import java.util.List;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
public interface OptimizationAlgorithm {

    List<List<Journey>> performOptimization(int startStation, int endStation);

}
