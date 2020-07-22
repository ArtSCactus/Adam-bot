package com.adam.dataserver.city;

import com.adam.dataserver.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@Service
public class CityService {
    @Autowired
    private CityRepository repository;

    public City save(City city){
       return repository.save(city);
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

    public City getByName(String name){
        Optional<City> city = repository.findByName(name);
        if (city.isPresent()){
            return city.get();
        } else {
            throw new NotFoundException();
        }
    }

    public void deleteByName(String name){
        repository.deleteByName(name);
    }

}
