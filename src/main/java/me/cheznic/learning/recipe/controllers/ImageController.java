package me.cheznic.learning.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.cheznic.learning.recipe.commands.RecipeCommand;
import me.cheznic.learning.recipe.exceptions.BadRequestException;
import me.cheznic.learning.recipe.services.ImageService;
import me.cheznic.learning.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Charles Nicoletti on 9/3/18
 */
@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;


    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {

        if (!isNumeric(id)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + id;
            log.warn(message);
            throw new BadRequestException(message);
        }

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {

        if (!isNumeric(id)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + id;
            log.warn(message);
            throw new BadRequestException(message);
        }

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {

        if (!isNumeric(id)) {
            String message = "Recipe identifier must be a positive integer.  Value received is: " + id;
            log.warn(message);
            throw new BadRequestException(message);
        }

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipeCommand.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    private boolean isNumeric(String s) {
        return s.matches("\\d+");
    }
}
