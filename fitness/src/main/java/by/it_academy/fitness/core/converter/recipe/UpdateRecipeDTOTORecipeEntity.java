package by.it_academy.fitness.core.converter.recipe;

import by.it_academy.fitness.core.dto.recipe.UpdateRecipeDTO;
import by.it_academy.fitness.entity.RecipeEntity;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Collectors;

@Component
public class UpdateRecipeDTOTORecipeEntity implements Converter<UpdateRecipeDTO, RecipeEntity> {
    private final AddIngredientDTOToIngredientEntity addIngredientDTOToIngredientEntity;

    public UpdateRecipeDTOTORecipeEntity(AddIngredientDTOToIngredientEntity addIngredientDTOToIngredientEntity) {
        this.addIngredientDTOToIngredientEntity = addIngredientDTOToIngredientEntity;
    }

    @Override
    public RecipeEntity convert(UpdateRecipeDTO source) {
        return new RecipeEntity(source.getUuid(), source.getDtUpdate(), source.getAddRecipeDTO().getTitle(), source.getAddRecipeDTO().getComposition().stream()
                .map(addIngredientDTOToIngredientEntity::convert)
                .collect(Collectors.toList()));
    }
}
