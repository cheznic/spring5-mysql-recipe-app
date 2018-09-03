package me.cheznic.learning.recipe.services;

import me.cheznic.learning.recipe.commands.IngredientCommand;

/**
 * Created by Charles Nicoletti on 9/2/18
 */
public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
