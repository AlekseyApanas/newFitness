package by.it_academy.fitness.service.api.product;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.entity.RecipeEntity;


public interface IRecipeService<T> {
    void create(RecipeEntity recipeEntity);

    Page<T> get(int page, int size);

    void update(RecipeEntity recipeEntity);
}
