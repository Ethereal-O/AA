package com.example.bookstore.Entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "mybookstore", catalog = "")
public class BookEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "author")
    private String author;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "inventory")
    private Integer inventory;

    @Basic
    @Column(name = "total_num")
    private Integer total_num;

    @Basic
    @Column(name = "ISBN_num")
    private Integer ISBN_num;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInventory() {return inventory;}

    public void setInventory(Integer inventory) {this.inventory = inventory;}

    public Integer getTotal_num() {return total_num;}

    public void setTotal_num(Integer total_num) {this.total_num = total_num;}

    public Integer getISBN_num() {return ISBN_num;}

    public void setISBN_num(Integer ISBN_num) {this.ISBN_num = ISBN_num;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return id == that.id && Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.equals(author, that.author) && Objects.equals(price, that.price) && Objects.equals(image, that.image) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, author, price, image, description);
    }
}
