package in.deepak.controller;

import in.deepak.dto.ProductCreateDTO;
import in.deepak.dto.ProductUpdateDTO;
import in.deepak.dto.StockAdjustmentDTO;
import in.deepak.entity.Product;
import in.deepak.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductCreateDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Product> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/stock/increase")
    public Product increaseStock(@PathVariable Long id, @Valid @RequestBody StockAdjustmentDTO dto) {
        return service.increaseStock(id, dto);
    }

    @PostMapping("/{id}/stock/decrease")
    public Product decreaseStock(@PathVariable Long id, @Valid @RequestBody StockAdjustmentDTO dto) {
        return service.decreaseStock(id, dto);
    }

    @GetMapping("/low-stock")
    public List<Product> lowStock() {
        return service.listLowStock();
    }
}
