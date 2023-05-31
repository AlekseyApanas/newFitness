package by.it_academy.fitness.core.converter.product;

import by.it_academy.fitness.core.dto.product.UpdateProductDTO;
import by.it_academy.fitness.entity.ProductEntity;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
@Component
public class UpdateProductDTOToEntity implements Converter<UpdateProductDTO, ProductEntity> {
    @Override
    public ProductEntity convert(UpdateProductDTO source) {
        return new ProductEntity(source.getUuid(),source.getDtUpdate(),
                source.getAddProductDTO().getTitle(),
                source.getAddProductDTO().getWeight(),
                source.getAddProductDTO().getCalories(),
                source.getAddProductDTO().getProteins(),
                source.getAddProductDTO().getFats(),
                source.getAddProductDTO().getCarbohydrates());
    }
}
