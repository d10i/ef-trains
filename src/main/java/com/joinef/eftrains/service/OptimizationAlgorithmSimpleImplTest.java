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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPerformOptimization() throws Exception {

        when(stationDao.count()).thenReturn(4);

        when(journeyService.findFrom(eq("0"), any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(1, new Journey.Builder().departureStation("0").arrivalStation("1").price(1).build());
                journeys.put(2, new Journey.Builder().departureStation("0").arrivalStation("2").price(7.0f).build());
                return journeys;
            }
        });

        when(journeyService.findFrom(eq("1"), any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(0, new Journey.Builder().departureStation("1").arrivalStation("0").price(1.0f).build());
                journeys.put(2, new Journey.Builder().departureStation("1").arrivalStation("2").price(1.0f).build());
                journeys.put(3, new Journey.Builder().departureStation("1").arrivalStation("3").price(7.0f).build());
                return journeys;
            }
        });

        when(journeyService.findFrom("2", any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(0, new Journey.Builder().departureStation("2").arrivalStation("0").price(7.0f).build());
                journeys.put(3, new Journey.Builder().departureStation("2").arrivalStation("3").price(1.0f).build());
                journeys.put(1, new Journey.Builder().departureStation("2").arrivalStation("1").price(1.0f).build());
                return journeys;
            }
        });

        when(journeyService.findFrom("3", any(DateTime.class))).thenAnswer(new Answer<HashMap<Integer, Journey>>() {
            @Override
            public HashMap<Integer, Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                HashMap<Integer, Journey> journeys = new HashMap<Integer, Journey>();
                journeys.put(1, new Journey.Builder().departureStation("3").arrivalStation("1").price(7.0f).build());
                journeys.put(2, new Journey.Builder().departureStation("3").arrivalStation("2").price(1.0f).build());
                return journeys;
            }
        });

        List<List<Journey>> journeys = optimizationAlgorithm.performOptimization(0, 3);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(0).

                        getDepartureStation(),

                0);
        Assert.assertEquals(route.get(1).

                        getDepartureStation(),

                1);
        Assert.assertEquals(route.get(2).

                        getDepartureStation(),

                2);

    }

    @Test
    public void testPerformOptimizationTime() throws Exception {

        final int testCount = 2500;
        //when(journeyDao.countStations()).thenReturn(testCount);
        //journeyService = new JourneyServiceImpl(false);

        when(journeyService.findFrom(anyString(), any(DateTime.class))).thenAnswer(new Answer<List<Journey>>() {
            @Override
            public List<Journey> answer(InvocationOnMock invocationOnMock) throws Throwable {
                int i = (Integer) invocationOnMock.getArguments()[0];

                List<Journey> journeys = new ArrayList<>();
                if (i + 1 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 1)).price(7.0f).build());
                if (i + 2 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 2)).price(6.0f).build());
                if (i + 3 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 3)).price(5.0f).build());
                if (i + 4 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 4)).price(8.0f).build());
                if (i + 5 <= testCount - 1)
                    journeys.add(new Journey.Builder().departureStation(String.valueOf(i)).arrivalStation(String.valueOf(i + 5)).price(10.0f).build());

                return journeys;
            }
        });

        /* for(int i =0; i < testCount; i++) {
            for (int j = 0; j < testCount; j++) {
                if(j == i+1 || j == i+2) {
                    when(journeyService.find(eq(i), eq(j), any(DateTime.class))).thenReturn(1f);
                    when(journeyService.find(eq(j), eq(i), any(DateTime.class))).thenReturn(1f);
                    continue;
                }

                when(journeyService.find(eq(i), eq(j), any(DateTime.class))).thenReturn(Float.NaN);
                when(journeyService.find(eq(j), eq(i), any(DateTime.class))).thenReturn(Float.NaN);
            }
        }*/

        when(stationDao.count()).thenReturn(testCount);

        List<List<Journey>> journeys =  optimizationAlgorithm.performOptimization(0, testCount - 1);
        List<Journey> route = journeys.get(0);
        Assert.assertEquals(route.get(route.size()-1).getDepartureStation(), testCount - 1);
    }
}