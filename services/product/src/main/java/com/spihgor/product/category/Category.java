package com.spihgor.product.category;


import com.spihgor.product.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
