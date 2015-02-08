package com.joinef.eftrains.service;

import com.joinef.eftrains.dao.StationDao;
import com.joinef.eftrains.entity.Journey;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OptimizationAlgorithmSimpleImplTest {


    @Mock
    private JourneyService journeyService;

    @Mock
    private StationDao stationDao;

    private OptimizationAlgorithmSimpleImpl optimizationAlgorithm;

    @Before
    public void setUp() throws Exception {
        optimizationAlgorithm = new OptimizationAlgorithmSimpleImpl();
        //journeyService = new JourneyServiceImpl(false);
        optimizationAlgorithm.setJourneyService(journeyService);
        optimizationAlgorithm.setStationDao(stationDao);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPerformOptimization() throws Exception {

        List<String> allStationKeys = new ArrayList<>();
        allStationKeys.add("0");
        allStationKeys.add("1");
        allStationKeys.add("2");
        allStationKeys.add("3");
        when(stationDao.findAllKeys()).thenReturn(allStationKeys);

        when(journeyService.findFrom(eq("0"), any(DateTime.class))).thenAnswer(new Answer<HashMap<String, Journey>>() {
            @Override
            public HashMap<String, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<String, Journey> journeys = new HashMap<>();
                journeys.put("1", new Journey.Builder().departureStation("0").arrivalStation("1").price(1).build());
                journeys.put("2", new Journey.Builder().departureStation("0").arrivalStation("2").price(7).build());
                return journeys;
            }
        });

        when(journeyService.findFrom(eq("1"), any(DateTime.class))).thenAnswer(new Answer<HashMap<String, Journey>>() {
            @Override
            public HashMap<String, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<String, Journey> journeys = new HashMap<>();
                journeys.put("0", new Journey.Builder().departureStation("1").arrivalStation("0").price(1).build());
                journeys.put("2", new Journey.Builder().departureStation("1").arrivalStation("2").price(1).build());
                journeys.put("3", new Journey.Builder().departureStation("1").arrivalStation("3").price(7).build());
                return journeys;
            }
        });

        when(journeyService.findFrom(eq("2"), any(DateTime.class))).thenAnswer(new Answer<HashMap<String, Journey>>() {
            @Override
            public HashMap<String, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<String, Journey> journeys = new HashMap<>();
                journeys.put("0", new Journey.Builder().departureStation("2").arrivalStation("0").price(7).build());
                journeys.put("3", new Journey.Builder().departureStation("2").arrivalStation("3").price(1).build());
                journeys.put("1", new Journey.Builder().departureStation("2").arrivalStation("1").price(1).build());
                return journeys;
            }
        });

        when(journeyService.findFrom(eq("3"), any(DateTime.class))).thenAnswer(new Answer<HashMap<String, Journey>>() {
            @Override
            public HashMap<String, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<String, Journey> journeys = new HashMap<>();
                journeys.put("1", new Journey.Builder().departureStation("3").arrivalStation("1").price(7).build());
                journeys.put("2", new Journey.Builder().departureStation("3").arrivalStation("2").price(1).build());
                return journeys;
            }
        });

        List<List<Journey>> journeys = optimizationAlgorithm.performOptimization(0, 3);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(0).getDepartureStation(), 0);
        Assert.assertEquals(route.get(1).getDepartureStation(), 1);
        Assert.assertEquals(route.get(2).getDepartureStation(), 2);
    }

    @Test
    public void testPerformOptimizationTime() throws Exception {

        final int testCount = 2500;
        //when(journeyDao.countStations()).thenReturn(testCount);
        //journeyService =

        List<String> allStationKeys = new ArrayList<>();
        for (int i = 0; i < testCount; i++) {
            allStationKeys.add(String.valueOf(i));
        }
        when(stationDao.findAllKeys()).thenReturn(allStationKeys);

        when(journeyService.findFrom(anyString(), any(DateTime.class))).thenAnswer(new Answer<List<Journey>>() {
            @Override
            public List<Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                String i = (String) invocationOnMock.getArguments()[0];

                List<Journey> journeys = new ArrayList<>();
                if (Integer.parseInt(i) + 1 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 1)).price(7).build());
                if (Integer.parseInt(i) + 2 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 2)).price(6).build());
                if (Integer.parseInt(i) + 3 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 3)).price(5).build());
                if (Integer.parseInt(i) + 4 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 4)).price(8).build());
                if (Integer.parseInt(i) + 5 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 5)).price(10).build());

                return journeys;
            }
        });

        when(stationDao.count()).thenReturn(testCount);

        List<List<Journey>> journeys = optimizationAlgorithm.performOptimization(0, testCount - 1);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(route.size() - 1).getDepartureStation(), testCount - 1);
    }
}