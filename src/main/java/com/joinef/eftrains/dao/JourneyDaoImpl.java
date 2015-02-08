package com.joinef.eftrains.dao;

import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 07/02/2015.
 */
@Repository
public class JourneyDaoImpl implements JourneyDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Journey> findFrom(String departureStation, DateTime departureTime) {
        String sql = "SELECT * FROM journey WHERE departure_station = ?";

        Connection conn = null;

        List<Journey> journeys = new ArrayList<>();

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
                        price(rs.getInt("price")).
                        departureTime(parseDateTime(rs.getTimestamp("departure_time"))).
                        arrivalTime(parseDateTime(rs.getTimestamp("arrival_time"))).
                        build();
                journeys.add(journey);
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
        if (timestamp == null) {
            return null;
        }

        return new DateTime(timestamp).withZone(DateTimeZone.forID("Europe/London"));
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
