package com.example.microservice3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product")
public class Product {
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Sku",unique = true)
    private String sku;
    @Column(name = "Product Image")
    private String productImage;
    @Column(name = "Product Name")
    private String productName;
    @ManyToOne
    @JoinColumn(name = "SubCategory Id")
    private SubCategory subCategory;
    @Column(name = "Short Description", length = 250)
    private String shortDescription;
    @Column(name = "Long Description", length = 600)
    private String longDescription;
    @Column(name = "Price")
    private Double price;
    @Column(name="Quantity")
    private Double quantity;
    @Column(name = "Discount Price")
    private Double discountPrice;
    @Column(name = "Active")
    private Boolean active;


}
