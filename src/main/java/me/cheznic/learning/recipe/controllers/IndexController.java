package me.cheznic.learning.recipe.controllers;

import me.cheznic.learning.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Charles Nicoletti on 8/24/18
 */
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index", "index.html"})
    public String getIndexPage (Model model) {

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
