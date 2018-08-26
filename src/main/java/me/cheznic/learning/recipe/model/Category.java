package me.cheznic.learning.recipe.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Charles Nicoletti on 8/25/18
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;


}
