package com.example.backendservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    private Boolean discount;
    private Double newPrice;
    private Integer percentage;

}
