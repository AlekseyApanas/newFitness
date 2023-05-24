package by.it_academy.fitness.facade.api.product;

import by.it_academy.fitness.core.dto.page.PageDTO;
import by.it_academy.fitness.core.dto.recipe.AddRecipeDTO;
import by.it_academy.fitness.core.dto.recipe.UpdateRecipeDTO;


public interface IRecipeFacade<T> {
    void create(AddRecipeDTO recipeDTO);

    PageDTO<T> get(int page, int size);

    void update(UpdateRecipeDTO recipeDTO);
}
