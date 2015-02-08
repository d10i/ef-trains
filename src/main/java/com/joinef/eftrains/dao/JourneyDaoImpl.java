package com.joinef.eftrains.dao;

import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jamie on 07/02/2015.
 */
@Repository
public class JourneyDaoImpl implements JourneyDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public float find(int startStation, int endStation, DateTime departureTime) {
        return 0;
    }

    @Override
    public List<Journey> findFrom(int startStation, DateTime departureTime) {
        return null;
    }

    @Override
    public int countStations() {
        String sql = "SELECT COUNT(DISTINCT departure_station) AS stationsCount FROM journeys";

        Connection conn = null;

        int count = Integer.MIN_VALUE;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("stationsCount");
            }
            rs.close();
            ps.close();
            return count;
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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
