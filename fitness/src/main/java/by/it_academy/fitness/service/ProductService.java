package by.it_academy.fitness.service;


import by.it_academy.fitness.core.dto.page.Page;
import by.it_academy.fitness.core.exception.CheckDoubleException;
import by.it_academy.fitness.core.exception.CheckVersionException;
import by.it_academy.fitness.core.exception.NotFoundException;
import by.it_academy.fitness.dao.api.product.IProductDao;
import by.it_academy.fitness.entity.ProductEntity;
import by.it_academy.fitness.service.api.product.IProductService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductService implements IProductService {
    private final IProductDao dao;

    private final ConversionService conversionService;

    public ProductService(IProductDao dao, ConversionService conversionService) {
        this.dao = dao;
        this.conversionService = conversionService;
    }

    @Override
    public void create(ProductEntity product) {
        ProductEntity productEntity = dao.findByTitle(product.getTitle());
        if (productEntity != null) {
            throw new CheckDoubleException("Продукт с таким названием уже существует");
        } else {
            dao.save(product);
        }
    }

    @Override
    public Page<ProductEntity> get(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        org.springframework.data.domain.Page<ProductEntity> all = dao.findAll(paging);
        List<ProductEntity> productsPages = all.getContent().stream()
                .map(s -> conversionService.convert(s, ProductEntity.class))
                .collect(Collectors.toList());
        return new Page<>(page, size, all.getTotalPages(), all.getTotalElements(), all.isFirst(), all.getNumberOfElements(), all.isLast(), productsPages);
    }

    @Override
    public void update(ProductEntity product) {
        ProductEntity productEntity = dao.findById(product.getUuid()).orElseThrow(() -> new NotFoundException("Такого продукта не существует"));
        if (productEntity.getDtUpdate().toEpochMilli() == product.getDtUpdate().toEpochMilli()) {
            productEntity.setTitle(product.getTitle());
            productEntity.setWeight(product.getWeight());
            productEntity.setCalories(product.getCalories());
            productEntity.setProteins(product.getProteins());
            productEntity.setFats(product.getFats());
            productEntity.setCarbohydrates(product.getCarbohydrates());
            dao.save(productEntity);
        } else throw new CheckVersionException("Такой версии не существует");
    }

    @Override
    public ProductEntity get(UUID id) {
        return this.dao.findById(id).orElseThrow(() -> new NotFoundException("Такого продукта не существует"));
    }
}