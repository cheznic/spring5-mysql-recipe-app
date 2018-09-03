package me.cheznic.learning.recipe.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Charles Nicoletti on 9/3/18
 */
public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file);
}
