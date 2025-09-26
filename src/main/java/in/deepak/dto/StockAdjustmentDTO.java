package in.deepak.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class StockAdjustmentDTO {

    @Min(1)
    private int amount;
}
