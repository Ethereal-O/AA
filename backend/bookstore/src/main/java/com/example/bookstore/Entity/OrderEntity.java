package com.example.bookstore.Entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "`order`", schema = "mybookstore", catalog = "")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uid")
    private UserEntity user;

    @Basic
    @Column(name = "order_time")
    private Date order_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser()
    {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getOrder_time() {return order_time;}

    public void setOrder_time(Date order_time) {this.order_time = order_time;}

//    @Transient
    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "order", orphanRemoval = true,fetch=FetchType.LAZY)
    private Set<OrderitemEntity> orderitems;
    public Set<OrderitemEntity> getOrderitems(){return orderitems;}
    public void setOrderitems(Set<OrderitemEntity> neworderitems){this.orderitems=neworderitems;}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id && user == that.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }
}
