package com.joinef.eftrains.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
@Repository
public class StationDaoImpl implements StationDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) AS stationsCount FROM station";

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

    @Override
    public List<String> findAllKeys() {
        String sql = "SELECT DISTINCT `key` FROM station";

        Connection conn = null;

        List<String> keys = new ArrayList<>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                keys.add(rs.getString("key"));
            }
            rs.close();
            ps.close();
            return keys;
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
