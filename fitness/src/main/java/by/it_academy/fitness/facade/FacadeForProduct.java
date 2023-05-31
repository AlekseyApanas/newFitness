package by.it_academy.fitness.facade;

import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.dto.product.AddProductDTO;
import by.it_academy.fitness.core.dto.product.ProductDTO;
import by.it_academy.fitness.core.dto.product.UpdateProductDTO;
import by.it_academy.fitness.entity.ProductEntity;
import by.it_academy.fitness.facade.api.product.IProductFacade;
import by.it_academy.fitness.service.api.product.IProductService;
import org.springframework.core.convert.ConversionService;

import java.util.UUID;

public class FacadeForProduct implements IProductFacade {
    private final IProductService productService;
    private final ConversionService conversionService;

    public FacadeForProduct(IProductService productService, ConversionService conversionService) {
        this.productService = productService;
        this.conversionService = conversionService;
    }

    @Override
    public void create(AddProductDTO productDTO) {
        productService.create(conversionService.convert(productDTO, ProductEntity.class));
    }

    @Override
    public Page get(int page, int size) {
        return productService.get(page, size);
    }

    @Override
    public void update(UpdateProductDTO productDTO) {
        productService.update(conversionService.convert(productDTO, ProductEntity.class));
    }

    @Override
    public ProductDTO get(UUID id) {
        return conversionService.convert(productService.get(id), ProductDTO.class);
    }
}
