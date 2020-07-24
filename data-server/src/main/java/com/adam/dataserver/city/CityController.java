package com.adam.dataserver.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@RestController
public class CityController {
    @Autowired
    private CityService service;

    @GetMapping(path = "/city/all")
    public Iterable<City> allCities() {
        return service.getAll();
    }

    @GetMapping(path="/city")
    public City getByName(@RequestParam String name) {
        return service.getByName(name);
    }
    @GetMapping(path="/city/description")
    public String getDescription(@RequestParam String name){
        return service.getByName(name).getDescription();
    }

    @DeleteMapping(path="/city")
    public void deleteCityByName(@RequestParam String name){
        service.deleteByName(name);
    }

    @DeleteMapping(path = "/city/{?}")
    public void deleteCity(@PathVariable("?") Long id) {
        service.delete(id);
    }

    @PostMapping(path = "/city")
    public City editCity(@Valid @RequestBody City city) {
       return service.save(city);
    }

    @PostMapping(path="/city/all")
    public ResponseEntity<HttpStatus> editMultipleCities(@Valid @RequestBody List<City> cities){
        return service.save(cities)? ResponseEntity.ok(HttpStatus.ACCEPTED)
                : ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }

}
