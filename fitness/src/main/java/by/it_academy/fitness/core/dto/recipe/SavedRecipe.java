package by.it_academy.fitness.core.dto.recipe;

import by.it_academy.fitness.entity.RecipeEntity;

import java.time.Instant;

public class SavedRecipe {
    private RecipeEntity addRecipeDTO;
    private Instant dtCreate;
    private Instant dtUpdate;

    public RecipeEntity getAddRecipeDTO() {
        return addRecipeDTO;
    }

    public Instant getDtCreate() {
        return dtCreate;
    }

    public Instant getDtUpdate() {
        return dtUpdate;
    }

    public SavedRecipe(RecipeEntity addRecipeDTO) {
        this.addRecipeDTO = addRecipeDTO;
        this.dtCreate = Instant.now();
        this.dtUpdate = this.dtCreate;
    }
}
