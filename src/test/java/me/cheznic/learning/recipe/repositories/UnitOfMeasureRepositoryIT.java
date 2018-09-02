package me.cheznic.learning.recipe.repositories;

import me.cheznic.learning.recipe.model.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;


    @Test
    public void findByDescription() {

        String uom = "Teaspoon";

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription(uom);

        assertEquals(uom, unitOfMeasure.get().getDescription());
    }
}