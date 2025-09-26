package in.deepak;

import in.deepak.dto.ProductCreateDTO;
import in.deepak.dto.StockAdjustmentDTO;
import in.deepak.entity.Product;
import in.deepak.exception.InvalidStockOperationException;
import in.deepak.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService service;

    private static Long createdId;

    @Test
    @Order(1)
    void createProduct_success() {
        ProductCreateDTO dto = new ProductCreateDTO();
        dto.setName("Widget");
        dto.setDescription("Sample widget");
        dto.setStockQuantity(10);
        dto.setLowStockThreshold(3);

        Product p = service.create(dto);
        assertNotNull(p.getId());
        assertEquals(10, p.getStockQuantity());
        createdId = p.getId();
    }

    @Test
    @Order(2)
    void increaseStock_success() {
        StockAdjustmentDTO dto = new StockAdjustmentDTO();
        dto.setAmount(5);
        Product updated = service.increaseStock(createdId, dto);
        assertEquals(15, updated.getStockQuantity());
    }

    @Test
    @Order(3)
    void decreaseStock_success() {
        StockAdjustmentDTO dto = new StockAdjustmentDTO();
        dto.setAmount(7);
        Product updated = service.decreaseStock(createdId, dto);
        assertEquals(8, updated.getStockQuantity());
    }

    @Test
    @Order(4)
    void decreaseStock_insufficient_throws() {
        StockAdjustmentDTO dto = new StockAdjustmentDTO();
        dto.setAmount(1000);
        assertThrows(InvalidStockOperationException.class, () -> service.decreaseStock(createdId, dto));
    }

    @Test
    @Order(5)
    void cannot_create_with_negative_stock() {
        ProductCreateDTO dto = new ProductCreateDTO();
        dto.setName("Bad");
        dto.setDescription("negative stock");
        dto.setStockQuantity(-5);
        assertThrows(InvalidStockOperationException.class, () -> service.create(dto));
    }
}
