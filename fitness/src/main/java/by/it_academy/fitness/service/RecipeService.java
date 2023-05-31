package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.dto.recipe.SavedRecipe;
import by.it_academy.fitness.core.exception.CheckDoubleException;
import by.it_academy.fitness.core.exception.CheckVersionException;
import by.it_academy.fitness.core.exception.NotFoundException;
import by.it_academy.fitness.dao.api.product.IRecipeDao;
import by.it_academy.fitness.entity.IngredientEntity;
import by.it_academy.fitness.entity.ProductEntity;
import by.it_academy.fitness.entity.RecipeEntity;
import by.it_academy.fitness.service.api.product.IProductService;
import by.it_academy.fitness.service.api.product.IRecipeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeService implements IRecipeService {
    private final IRecipeDao dao;

    private final IProductService productService;
    private final ConversionService conversionService;

    public RecipeService(IRecipeDao dao, IProductService productService, ConversionService conversionService) {
        this.dao = dao;
        this.productService = productService;
        this.conversionService = conversionService;
    }

    @Override
    public void create(RecipeEntity recipe) {
        RecipeEntity recipeEntity = dao.findByTitle(recipe.getTitle());
        if (recipeEntity != null) {
            throw new CheckDoubleException("Рецепт с таким названием уже существует");
        } else {
            List<IngredientEntity> ingredientDTOList = recipe.getComposition();
            List<IngredientEntity> list = ingredientDTOList.stream()
                    .map(s -> new IngredientEntity(conversionService.convert(productService.get(s.getProduct().getUuid()), ProductEntity.class),
                            s.getWeight())).collect(Collectors.toList());
            SavedRecipe savedRecipe = new SavedRecipe(recipe);
            dao.save(new RecipeEntity(savedRecipe.getDtCreate(),
                    savedRecipe.getDtUpdate(), savedRecipe.getAddRecipeDTO().getTitle(),
                    list));
        }
    }

    @Override
    public Page<RecipeEntity> get(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        org.springframework.data.domain.Page<RecipeEntity> all = dao.findAll(paging);
        List<RecipeEntity> recipePages = new ArrayList<>();
        List<RecipeEntity> content = all.getContent();
        for (RecipeEntity recipeEntity : content) {
            List<IngredientEntity> ingredientList = recipeEntity.getComposition().stream().
                    map(s -> new IngredientEntity(productService.get(
                            s.getProduct().getUuid()),
                            s.getWeight(),
                            s.getWeight() * s.getProduct().getCalories() / s.getProduct().getWeight(),
                            (s.getWeight() * (double) s.getProduct().getCalories() / s.getProduct().getWeight()),
                            (s.getWeight() * (double) s.getProduct().getCalories() / s.getProduct().getWeight()),
                            (s.getWeight() * (double) s.getProduct().getCalories() / s.getProduct().getWeight())
                    )).collect(Collectors.toList());
            Integer weight = ingredientList.stream().mapToInt(IngredientEntity::getWeight).sum();
            Integer calories = ingredientList.stream().mapToInt(IngredientEntity::getCalories).sum();
            Double proteins = ingredientList.stream().mapToDouble(IngredientEntity::getProteins).sum();
            Double fats = ingredientList.stream().mapToDouble(IngredientEntity::getFats).sum();
            Double carbohydrates = ingredientList.stream().mapToDouble(IngredientEntity::getCarbohydrates).sum();
            recipePages.add(new RecipeEntity(recipeEntity.getUuid(), recipeEntity.getDtCreate(), recipeEntity.getDtUpdate(), recipeEntity.getTitle(), ingredientList,
                    weight, calories, proteins, fats, carbohydrates));
        }
        return new Page<>(page, size, all.getTotalPages(), all.getTotalElements(), all.isFirst(), all.getNumberOfElements(), all.isLast(), recipePages);
    }


    @Override
    public void update(RecipeEntity recipe) {
        RecipeEntity recipeEntity = dao.findById(recipe.getUuid()).orElseThrow(() -> new NotFoundException("Такого рецепта не существует"));
        if (recipe.getDtUpdate().toEpochMilli() == recipeEntity.getDtUpdate().toEpochMilli()) {
            List<IngredientEntity> ingredientDTOList = recipe.getComposition();
            List<IngredientEntity> list = ingredientDTOList.stream()
                    .map(s -> new IngredientEntity(s.getProduct(),
                            s.getWeight())).collect(Collectors.toList());
            recipeEntity.setTitle(recipe.getTitle());
            recipeEntity.setComposition(list);
            dao.save(recipeEntity);
        } else throw new CheckVersionException("Такой версии не существует");
    }
}
