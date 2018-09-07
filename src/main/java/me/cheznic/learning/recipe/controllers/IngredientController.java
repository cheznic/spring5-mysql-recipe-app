package me.cheznic.learning.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.cheznic.learning.recipe.commands.IngredientCommand;
import me.cheznic.learning.recipe.commands.RecipeCommand;
import me.cheznic.learning.recipe.commands.UnitOfMeasureCommand;
import me.cheznic.learning.recipe.exceptions.BadRequestException;
import me.cheznic.learning.recipe.services.IngredientService;
import me.cheznic.learning.recipe.services.RecipeService;
import me.cheznic.learning.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Charles Nicoletti on 9/2/18
 */
@SuppressWarnings("SameReturnValue")
@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        if (isNotNumeric(recipeId)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + recipeId;
            log.warn(message);
            throw new BadRequestException(message);
        }

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredients(@PathVariable String recipeId,
                                        @PathVariable String ingredientId,
                                        Model model) {

        if (isNotNumeric(recipeId)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + recipeId;
            log.warn(message);
            throw new BadRequestException(message);
        }

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        log.debug("Showing ingredient of id: " + ingredientId);

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model) {

        if (isNotNumeric(recipeId)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + recipeId;
            log.warn(message);
            throw new BadRequestException(message);
        }

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }


    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model) {

        if (isNotNumeric(recipeId)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + recipeId;
            log.warn(message);
            throw new BadRequestException(message);
        }

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        log.debug("");
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeId, @ModelAttribute IngredientCommand command) {

        if (isNotNumeric(recipeId)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + recipeId;
            log.warn(message);
            throw new BadRequestException(message);
        }

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteById(@PathVariable String ingredientId, @PathVariable String recipeId) {

        if (isNotNumeric(recipeId)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + recipeId;
            log.warn(message);
            throw new BadRequestException(message);
        }

        log.debug("Deleting ingredient with id: " + ingredientId + "from recipe: " + recipeId);

        ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    private boolean isNotNumeric(String s) {
        return !s.matches("\\d+");
    }
}
