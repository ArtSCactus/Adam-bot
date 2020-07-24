package com.adam.dataserver.city;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(name = "name")
    @Pattern(regexp = "[A-zА-я\\-_\\s]{3,}")
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @Column(name = "description", length = 1500)
    @Pattern(regexp = "[A-zА-я0-9\\-_.?;:!,'\\[\\]\"\\s\\n)(#@$]{3,}")
    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    public City() {
    }

    public City(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(getId(), city.getId()) &&
                Objects.equals(getName(), city.getName()) &&
                Objects.equals(getDescription(), city.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
