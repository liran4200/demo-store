package com.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Getter @Setter @NoArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="category_id")
    private Long categoryId;
    private String name;

    @JsonManagedReference()
    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    private List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(name, category.name) && Objects.equals(products, category.products);
    }

    public void addProduct( Product p ) {
        products.add(p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, products);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
