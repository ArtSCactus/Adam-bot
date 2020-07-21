package com.adam.dataserver.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@Service
public class CityService {
    @Autowired
    private CityRepository repository;

    public void save(City city){
        repository.save(city);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public City getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<City> getAll(){
        return repository.findAll();
    }

}
