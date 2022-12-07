package com.example.bookstore.Entity;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Objects;

@Entity
@Table(name = "orderitem", schema = "mybookstore", catalog = "")
public class OrderitemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="oid")
    private OrderEntity order;
    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bid")
    private BookEntity book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderEntity getOrder()
    {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderitemEntity that = (OrderitemEntity) o;
        return id == that.id && order== that.order && book == that.book;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, book);
    }
}
