package in.deepak.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Partial update DTO — fields can be null if not updating.
 */
@Data
public class ProductUpdateDTO {

    private String name;
    private String description;

    // If provided, must be >= 0; validation checked in service layer for null handling.
    private Integer stockQuantity;

    private Integer lowStockThreshold;
}
