package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.page.PageDTO;
import by.it_academy.fitness.core.dto.product.AddProductDTO;
import by.it_academy.fitness.core.dto.product.UpdateProductDTO;
import by.it_academy.fitness.facade.api.product.IProductFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
  private IProductFacade productFacade;

    public ProductController(IProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PutMapping(path = "/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID userUUID,
                                    @PathVariable("dt_update") Instant dtUpdate,
                                    @RequestBody @Valid AddProductDTO addProductDTO) {
        productFacade.update(new UpdateProductDTO(addProductDTO, dtUpdate, userUUID));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AddProductDTO addProductDTO) {
        productFacade.create(addProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageDTO> get(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "20") @Min(0) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(productFacade.get(page, size));
    }
}
