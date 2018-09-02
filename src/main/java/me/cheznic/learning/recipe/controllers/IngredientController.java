package me.cheznic.learning.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.cheznic.learning.recipe.services.IngredientService;
import me.cheznic.learning.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

;

/**
 * Created by Charles Nicoletti on 9/2/18
 */
@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredients(@PathVariable String recipeId,
                                        @PathVariable String ingredientId,
                                        Model model) {


        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        log.debug("Showing ingredient of id: " + ingredientId);

        return "recipe/ingredient/show";
    }
}
