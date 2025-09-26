package in.deepak.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    // Always non-negative. Business rules in service layer enforce this.
    private int stockQuantity;

    // Optional. If null => no threshold configured.
    private Integer lowStockThreshold;
}
