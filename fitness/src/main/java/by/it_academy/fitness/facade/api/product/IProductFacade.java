package by.it_academy.fitness.facade.api.product;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.dto.product.AddProductDTO;
import by.it_academy.fitness.core.dto.product.ProductDTO;
import by.it_academy.fitness.core.dto.product.UpdateProductDTO;

import java.util.UUID;


public interface IProductFacade<T> {
    void create(AddProductDTO productDTO);

    Page<T> get(int page, int size);

    void update(UpdateProductDTO productDTO);
    ProductDTO get(UUID id);
}
