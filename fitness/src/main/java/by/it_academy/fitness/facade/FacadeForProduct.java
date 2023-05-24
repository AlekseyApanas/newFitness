package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.page.PageDTO;
import by.it_academy.fitness.core.dto.product.AddProductDTO;
import by.it_academy.fitness.core.dto.product.ProductDTO;
import by.it_academy.fitness.core.dto.product.UpdateProductDTO;
import by.it_academy.fitness.facade.api.product.IProductFacade;
import by.it_academy.fitness.service.api.product.IProductService;

import java.util.UUID;

public class FacadeForProduct implements IProductFacade {
    private final IProductService productService;

    public FacadeForProduct(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void create(AddProductDTO productDTO) {
        productService.create(productDTO);
    }

    @Override
    public PageDTO get(int page, int size) {
        return productService.get(page, size);
    }

    @Override
    public void update(UpdateProductDTO productDTO) {
        productService.update(productDTO);
    }

    @Override
    public ProductDTO get(UUID id) {
      return   productService.get(id);
    }
}
