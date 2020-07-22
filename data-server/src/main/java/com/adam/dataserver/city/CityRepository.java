package com.adam.dataserver.city;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findById(Long id);

    Optional<City> findByName(String name);

    City getByName(String name);

    void deleteById(Long id);

    void deleteByName(String name);

    List<City> findAll();




}
