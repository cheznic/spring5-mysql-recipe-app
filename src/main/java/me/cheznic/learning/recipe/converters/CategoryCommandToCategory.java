package me.cheznic.learning.recipe.converters;

import lombok.Synchronized;
import me.cheznic.learning.recipe.commands.CategoryCommand;
import me.cheznic.learning.recipe.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Charles Nicoletti on 9/1/18
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
