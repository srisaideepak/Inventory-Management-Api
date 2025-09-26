package in.deepak.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductCreateDTO {

    @NotBlank
    private String name;

    private String description;

    @Min(value = 0)
    private int stockQuantity;

    private Integer lowStockThreshold;
}
