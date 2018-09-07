package me.cheznic.learning.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.cheznic.learning.recipe.commands.RecipeCommand;
import me.cheznic.learning.recipe.exceptions.BadRequestException;
import me.cheznic.learning.recipe.exceptions.NotFoundException;
import me.cheznic.learning.recipe.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Charles Nicoletti on 9/1/18
 */
@SuppressWarnings("SameReturnValue")
@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        if (isNotNumeric(id)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + id;
            log.warn(message);
            throw new BadRequestException(message);
        }

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {

        if (isNotNumeric(id)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + id;
            log.warn(message);
            throw new BadRequestException(message);
        }

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {

        if (isNotNumeric(id)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + id;
            log.warn(message);
            throw new BadRequestException(message);
        }

        recipeService.deleteById(Long.valueOf(id));
        log.debug("Deleting id: " + id);

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", e);

        return modelAndView;
    }

    private boolean isNotNumeric(String s) {
        return !s.matches("\\d+");
    }
}
