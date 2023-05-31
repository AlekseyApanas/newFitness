package by.it_academy.fitness.entity;

import by.it_academy.fitness.core.converter.InstantToLongConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "fitness", name = "recipe")
public class RecipeEntity {
    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @JsonSerialize(converter = InstantToLongConverter.Serializer.class)
    @Column(name = "dt_create")
    private Instant dtCreate;
    @JsonSerialize(converter = InstantToLongConverter.Serializer.class)
    @Version
    @Column(name = "dt_update")
    private Instant dtUpdate;
    @Column(name = "title")
    private String title;
    @ElementCollection
    @CollectionTable(
            schema = "fitness",
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_uuid")
    )
    private List<IngredientEntity> composition;

    private Integer weight;

    private Integer calories;

    private Double proteins;
    private Double fats;

    private Double carbohydrates;


    public RecipeEntity() {
    }

    public RecipeEntity(String title, List<IngredientEntity> composition) {
        this.title = title;
        this.composition = composition;
    }

    public RecipeEntity(UUID uuid, Instant dtUpdate, String title, List<IngredientEntity> composition) {
        this.uuid = uuid;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
    }

    public RecipeEntity(UUID uuid, Instant dtCreate, Instant dtUpdate, String title, List<IngredientEntity> composition, Integer weight, Integer calories, Double proteins, Double fats, Double carbohydrates) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public RecipeEntity(UUID uuid, Instant dtCreate, Instant dtUpdate, String title, List<IngredientEntity> composition) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
    }


    public RecipeEntity(Instant dtCreate, Instant dtUpdate, String title, List<IngredientEntity> composition) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Instant getDtCreate() {
        return dtCreate;
    }

    public Instant getDtUpdate() {
        return dtUpdate;
    }

    public String getTitle() {
        return title;
    }

    public List<IngredientEntity> getComposition() {
        return composition;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setComposition(List<IngredientEntity> composition) {
        this.composition = composition;
    }

}
