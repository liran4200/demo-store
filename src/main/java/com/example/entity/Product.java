package com.example.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Product {

    // may be abstract product
    private @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
              Long     id;
    private String   name;
    private String   description;
    private float    price;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=true)
    private Category category;

    public Product() { }

    public Product(Long id, String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Float.compare(product.price, price) == 0 && id.equals(product.id) && name.equals(product.name) && description.equals(product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price);
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
