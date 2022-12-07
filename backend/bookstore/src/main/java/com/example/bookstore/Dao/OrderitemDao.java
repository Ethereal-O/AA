package com.example.bookstore.Dao;

import com.example.bookstore.Entity.OrderEntity;
import com.example.bookstore.Entity.OrderitemEntity;

import java.util.List;

public interface OrderitemDao {
    public boolean addOrderitem(OrderitemEntity orderitem);
    public boolean updateOrderitem(OrderitemEntity orderitem);
    public boolean deleteOrderitem(OrderitemEntity orderitem);
    public boolean addOrderitemUseTX(Integer oid, Integer bid);
    //查询
    //根据唯一标识查询单个实体
    public OrderitemEntity findOrderitemById(Integer id);
    public List<OrderitemEntity> findOrderitemsByBookId(Integer bookid);
    //根据实体的条件查询多个实体
    public List<OrderitemEntity> findOrderitemByExample(OrderitemEntity orderitem);
    //得到所有实体
    public List<OrderitemEntity> findallOrderitem();
}
