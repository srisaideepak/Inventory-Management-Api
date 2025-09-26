package in.deepak.service;

import in.deepak.dto.ProductCreateDTO;
import in.deepak.dto.ProductUpdateDTO;
import in.deepak.dto.StockAdjustmentDTO;
import in.deepak.entity.Product;
import in.deepak.exception.InvalidStockOperationException;
import in.deepak.exception.NotFoundException;
import in.deepak.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product create(ProductCreateDTO dto) {
        if (dto.getStockQuantity() < 0)
            throw new InvalidStockOperationException("stockQuantity cannot be negative");
        if (dto.getLowStockThreshold() != null && dto.getLowStockThreshold() < 0)
            throw new InvalidStockOperationException("lowStockThreshold cannot be negative");

        Product p = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .stockQuantity(dto.getStockQuantity())
                .lowStockThreshold(dto.getLowStockThreshold())
                .build();
        return repository.save(p);
    }

    public List<Product> listAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public Product update(Long id, ProductUpdateDTO dto) {
        Product p = getById(id);

        if (dto.getStockQuantity() != null && dto.getStockQuantity() < 0)
            throw new InvalidStockOperationException("stockQuantity cannot be negative");
        if (dto.getLowStockThreshold() != null && dto.getLowStockThreshold() < 0)
            throw new InvalidStockOperationException("lowStockThreshold cannot be negative");

        if (dto.getName() != null) p.setName(dto.getName());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getStockQuantity() != null) p.setStockQuantity(dto.getStockQuantity());
        if (dto.getLowStockThreshold() != null) p.setLowStockThreshold(dto.getLowStockThreshold());

        return repository.save(p);
    }

    public void delete(Long id) {
        Product p = getById(id);
        repository.delete(p);
    }

    @Transactional
    public Product increaseStock(Long id, StockAdjustmentDTO dto) {
        if (dto.getAmount() <= 0)
            throw new InvalidStockOperationException("amount must be a positive integer");

        Product p = getById(id);
        p.setStockQuantity(p.getStockQuantity() + dto.getAmount());
        return repository.save(p);
    }

    @Transactional
    public Product decreaseStock(Long id, StockAdjustmentDTO dto) {
        if (dto.getAmount() <= 0)
            throw new InvalidStockOperationException("amount must be a positive integer");

        Product p = getById(id);
        if (p.getStockQuantity() - dto.getAmount() < 0)
            throw new InvalidStockOperationException("Insufficient stock available");
        p.setStockQuantity(p.getStockQuantity() - dto.getAmount());
        return repository.save(p);
    }

    public List<Product> listLowStock() {
        return repository.findByLowStockThresholdNotNull().stream()
                .filter(p -> p.getStockQuantity() < p.getLowStockThreshold())
                .collect(Collectors.toList());
    }
}
