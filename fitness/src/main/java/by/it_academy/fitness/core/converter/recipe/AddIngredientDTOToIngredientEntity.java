package by.it_academy.fitness.core.converter.recipe;

import by.it_academy.fitness.core.dto.recipe.ingredient.AddIngredientDTO;
import by.it_academy.fitness.entity.IngredientEntity;
import by.it_academy.fitness.service.api.product.IProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddIngredientDTOToIngredientEntity implements Converter<AddIngredientDTO, IngredientEntity> {
    private final IProductService productService;

    public AddIngredientDTOToIngredientEntity(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public IngredientEntity convert(AddIngredientDTO source) {
        return new IngredientEntity(productService.get(source.getProduct()), source.getWeight());
    }
}
