package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.dto.recipe.AddRecipeDTO;
import by.it_academy.fitness.core.dto.recipe.UpdateRecipeDTO;
import by.it_academy.fitness.entity.RecipeEntity;
import by.it_academy.fitness.facade.api.product.IRecipeFacade;
import by.it_academy.fitness.service.api.product.IRecipeService;
import org.springframework.core.convert.ConversionService;

public class FacadeForRecipe implements IRecipeFacade {
    private final IRecipeService recipeService;
    private final ConversionService conversionService;

    public FacadeForRecipe(IRecipeService recipeService, ConversionService conversionService) {
        this.recipeService = recipeService;
        this.conversionService = conversionService;
    }

    @Override
    public void create(AddRecipeDTO recipeDTO) {
        recipeService.create(conversionService.convert(recipeDTO, RecipeEntity.class));
    }

    @Override
    public Page get(int page, int size) {
        return recipeService.get(page, size);
    }

    @Override
    public void update(UpdateRecipeDTO recipeDTO) {
        recipeService.update(conversionService.convert(recipeDTO, RecipeEntity.class));
    }
}
