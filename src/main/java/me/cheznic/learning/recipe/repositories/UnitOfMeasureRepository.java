package me.cheznic.learning.recipe.repositories;

import me.cheznic.learning.recipe.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Charles Nicoletti on 8/25/18
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    public Optional<UnitOfMeasure> findByName(String name);

    public Optional<UnitOfMeasure> findByAbbr(String name);
}
