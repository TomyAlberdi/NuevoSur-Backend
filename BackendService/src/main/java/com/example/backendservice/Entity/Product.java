package com.example.backendservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;
    
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Image> images;
    
    @NotNull
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    
    @NotNull
    @Column(name = "provider_id", nullable = false)
    private Long providerId;
    
    @NotNull
    @Column(name = "code", nullable = false)
    private Long code;
    
    @NotNull
    @Column(name = "measures", nullable = false)
    private String measures;
    
    @NotNull
    @Column(name = "m2_per_box", nullable = false)
    private Double m2PerBox;
    
    @NotNull
    @Column(name = "price_unit", nullable = false)
    private String priceUnit;
    
    @NotNull
    @Column(name = "sales_unit", nullable = false)
    private String salesUnit;
    
    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;
    
    @NotNull
    @Column(name = "quality", nullable = false)
    private String quality;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Tag> tags;
    
    @Embedded
    private Discount discount;
    
}
