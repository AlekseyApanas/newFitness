package by.it_academy.fitness.service.api.product;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.entity.ProductEntity;

import java.util.UUID;


public interface IProductService<T> {
    void create(ProductEntity productEntity);

    Page<T> get(int page, int size);

    void update(ProductEntity productEntity);
    ProductEntity get(UUID id);
}
