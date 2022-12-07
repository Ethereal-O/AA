package com.example.bookstore.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user", schema = "mybookstore", catalog = "")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "type")
    private Integer type;

    @Basic
    @Column(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    //    @Transient
    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "user", orphanRemoval = true,fetch=FetchType.LAZY)
    private Set<OrderEntity> orders;

    public Set<OrderEntity> getOrders(){return orders;}
    public void setOrders(Set<OrderEntity> neworders){this.orders=neworders;}

//    @Transient
    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "user", orphanRemoval = true,fetch=FetchType.LAZY)
    private Set<CartEntity> carts;

    public Set<CartEntity> getCarts(){return carts;}
    public void setCarts(Set<CartEntity> newcarts){this.carts=newcarts;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, type);
    }
}
