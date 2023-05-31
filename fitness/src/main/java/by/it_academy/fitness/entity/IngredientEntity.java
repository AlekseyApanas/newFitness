package by.it_academy.fitness.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class IngredientEntity {
    @ManyToOne
    @JoinColumn(
            name = "product_uuid"
    )
    private ProductEntity product;
    @Column(name = "weight")
    private Integer weight;

    public IngredientEntity(ProductEntity product, Integer weight) {
        this.product = product;
        this.weight = weight;
    }


    private Integer calories;

    private Double proteins;

    private Double fats;

    private Double carbohydrates;

    public IngredientEntity() {
    }

    public IngredientEntity(ProductEntity product, Integer weight, Integer calories, Double proteins, Double fats, Double carbohydrates) {
        this.product = product;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getCalories() {
        return calories;
    }

    public Double getProteins() {
        return proteins;
    }

    public Double getFats() {
        return fats;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }
}
