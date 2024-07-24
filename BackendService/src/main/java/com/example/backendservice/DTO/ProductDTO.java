package com.example.backendservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    
    private Long id;
    private String name;
    private String description;
    private Integer discountPercentage;
    private Double discountNewPrice;
    private String measures;
    private Double m2PerBox;
    private String priceUnit;
    private String salesUnit;
    private Double price;
    private String quality;
    
    private String category;
    private String provider;
    
    private List<String> tags;
    private List<String> image;
}
