package in.deepak.repository;

import in.deepak.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // helpful convenience: find products that have a threshold set
    List<Product> findByLowStockThresholdNotNull();
}
