package com.joinef.eftrains.dao;

import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jamie on 07/02/2015.
 */
@Repository
public class JourneyDaoImpl implements JourneyDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public Map<String, Journey> findFrom(String departureStation, DateTime departureTime) {
        String sql = "SELECT * FROM journey WHERE departure_station = ?";

        Connection conn = null;

        Map<String, Journey> journeys = new HashMap<>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, departureStation);
            ResultSet rs = ps.executeQuery();
            Journey journey;
            while (rs.next()) {
                journey = new Journey.Builder().
                        departureStation(rs.getString("departure_station")).
                        arrivalStation(rs.getString("arrival_station")).
                        price(rs.getFloat("price")).
                        departureTime(parseDateTime(rs.getTimestamp("departure_time"))).
                        arrivalTime(parseDateTime(rs.getTimestamp("arrival_time"))).
                        build();
                journeys.put(journey.getArrivalStation(), journey);
            }
            rs.close();
            ps.close();
            return journeys;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    private DateTime parseDateTime(Timestamp timestamp) {
        if (timestamp.getTime() <= 0) {
            return null;
        }

        return new DateTime(timestamp).withZone(DateTimeZone.forID("Europe/London"));
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
