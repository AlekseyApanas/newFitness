package by.it_academy.fitness.core.converter.recipe;

import by.it_academy.fitness.core.dto.recipe.AddRecipeDTO;
import by.it_academy.fitness.entity.RecipeEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AddRecipeToProductEntity implements Converter<AddRecipeDTO, RecipeEntity> {
    private final AddIngredientDTOToIngredientEntity addIngredientDTOToIngredientEntity;

    public AddRecipeToProductEntity(AddIngredientDTOToIngredientEntity addIngredientDTOToIngredientEntity) {
        this.addIngredientDTOToIngredientEntity = addIngredientDTOToIngredientEntity;
    }

    @Override
    public RecipeEntity convert(AddRecipeDTO addRecipeDTO) {
        return new RecipeEntity(addRecipeDTO.getTitle(), addRecipeDTO.getComposition().stream()
                .map(addIngredientDTOToIngredientEntity::convert)
                .collect(Collectors.toList()));
    }
}
