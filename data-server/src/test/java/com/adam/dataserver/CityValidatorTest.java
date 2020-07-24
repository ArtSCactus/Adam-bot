package com.adam.dataserver;


import com.adam.dataserver.city.City;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author ArtSCactus
 * @version 1.0
 */
public class CityValidatorTest {
    private Validator validator;

    @Before
    public void init(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    @Test
    public void nameValidationShouldFailTheTest(){
        City city = new City();
        city.setName("!VERY IMPRESSIVE CITY NAME!!!! or not?");
        city.setDescription("Totally normal description");
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void descriptionValidationShouldFailTheTest() {
        City city = new City();
        city.setName("Totally correct name");
        city.setDescription("<img src=\"\" onerror=\"someHackScript()\">");
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyNameShouldFailTheTest() {
        City city = new City();
        city.setName("");
        city.setDescription("Correct description");
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyDescriptionShouldFailTheTest() {
        City city = new City();
        city.setName("Name");
        city.setDescription("");
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void nullNameShouldFailTheTest() {
        City city = new City();
        city.setName(null);
        city.setDescription("");
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void nullDescriptionShouldFailTheTest() {
        City city = new City();
        city.setName("Name");
        city.setDescription(null);
        Set<ConstraintViolation<City>> violations = validator.validate(city);
        Assert.assertFalse(violations.isEmpty());
    }



}
