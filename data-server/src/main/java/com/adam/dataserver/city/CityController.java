package com.adam.dataserver.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@RestController
public class CityController {
    @Autowired
    private CityService service;

    @GetMapping(path = "/city")
    public Iterable<City> allCities() {
        return service.getAll();
    }

    @DeleteMapping(path = "/city/{?}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCity(@PathVariable("?") Long id) {
        service.delete(id);
    }

    @PostMapping(path = "/city")
    @ResponseStatus(value = HttpStatus.OK)
    public void editCities(@RequestBody City city) {
        service.save(city);
    }

}
