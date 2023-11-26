package com.example.microservice3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SubCategories")
public class SubCategory {
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="Subcategory Name")
    private String subCategoryName;
    @ManyToOne
    @JoinColumn(name = "Category Id")
    private Category category;
    @Column(name = "Active")
    private Boolean active;
    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> product;


}
