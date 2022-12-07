package com.example.bookstore.Dao;

import com.example.bookstore.Entity.OrderEntity;

import java.util.List;

public interface OrderDao {
    public boolean addOrder(OrderEntity order);
    public boolean updateOrder(OrderEntity order);
    public boolean deleteOrder(OrderEntity order);
    public boolean addOrderUseTX(OrderEntity order);
    //查询
    //根据唯一标识查询单个实体
    public OrderEntity findOrderById(Integer id);
    //根据实体的条件查询多个实体
    public List<OrderEntity> findOrderByExample(OrderEntity order);
    //得到所有实体
    public List<OrderEntity> findallOrder();

    public Integer findmaxOrderid();
}
