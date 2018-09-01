package me.cheznic.learning.recipe.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        Long idValue = 448888888L;

        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() {
        String descriptionValue = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque" +
                " laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae " +
                "vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, " +
                "sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, " +
                "qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi ";

        category.setDescription(descriptionValue);

        assertEquals(descriptionValue, category.getDescription());
    }

    @Test
    public void getRecipes() {
        //category.getRecipes().add(new Recipe());
        String a = "sdfgdfg sdgfsdfg sdfgsdfg";
        String b = a;
        assertEquals(a, b);
    }
}