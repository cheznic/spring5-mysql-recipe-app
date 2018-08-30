package me.cheznic.learning.recipe.services;

import me.cheznic.learning.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
