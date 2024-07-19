package com.example.backendservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCardDTO {
    
    private Long id;
    private String name;
    private Double price;
    private String salesUnit;
    private Integer discountPercentage;
    private Double discountNewPrice;
    private String image;
    
}
