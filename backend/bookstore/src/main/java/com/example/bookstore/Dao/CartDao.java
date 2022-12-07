package com.example.bookstore.Dao;



import com.example.bookstore.Entity.CartEntity;

import java.util.List;

public interface CartDao {
    public boolean addCart(CartEntity cart);
    public boolean updateCart(CartEntity cart);
    public boolean deleteCart(CartEntity cart);
    public boolean deleteCartByIdUseTX(Integer id);
    //查询
    //根据唯一标识查询单个实体
    public CartEntity findCartById(Integer id);
    public List<CartEntity> findCartsByBookId(Integer bookid);
    //根据实体的条件查询多个实体
    public List<CartEntity> findCartByExample(CartEntity cart);
    //得到所有实体
    public List<CartEntity> findallCart();
}
