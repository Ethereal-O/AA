package com.example.bookstore.Repository;

import com.example.bookstore.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    @Query(value = "from OrderEntity where id=:id")
    public OrderEntity findOrderById(Integer id);
    @Query(value = "from OrderEntity")
    public List<OrderEntity> findallOrder();

    @Query(value = "select max(id) from `order`",nativeQuery = true)
    public Integer findmaxOrderid();

    @Modifying
    @Query(value="insert into `order` values(:id,:uid,:order_time)",nativeQuery = true)
    void addOrderUseTX(Integer id, Integer uid, Date order_time);
}
