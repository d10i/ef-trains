package com.joinef.eftrains.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
@Repository
public interface StationDao {

    int count();

    List<String> findAllKeys();
}
