package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.page.PageDTO;
import by.it_academy.fitness.core.dto.recipe.AddRecipeDTO;
import by.it_academy.fitness.core.dto.recipe.UpdateRecipeDTO;
import by.it_academy.fitness.facade.api.product.IRecipeFacade;
import by.it_academy.fitness.service.RecipeService;

public class FacadeForRecipe implements IRecipeFacade {
    private final RecipeService recipeService;

    public FacadeForRecipe(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void create(AddRecipeDTO recipeDTO) {
        recipeService.create(recipeDTO);
    }

    @Override
    public PageDTO get(int page, int size) {
        return recipeService.get(page, size);
    }

    @Override
    public void update(UpdateRecipeDTO recipeDTO) {
        recipeService.update(recipeDTO);
    }
}
