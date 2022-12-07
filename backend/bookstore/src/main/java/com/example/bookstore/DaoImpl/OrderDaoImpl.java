package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.OrderDao;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Entity.CartEntity;
import com.example.bookstore.Entity.OrderEntity;
import com.example.bookstore.Redis.RedisUtil;
import com.example.bookstore.Repository.OrderRepository;
import com.example.bookstore.Utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderrepository;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean addOrder(OrderEntity order) {
        orderrepository.save(order);
        redisUtil.set("order"+order.getId(),order);
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.save(order);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean updateOrder(OrderEntity order) {
        orderrepository.save(order);
        redisUtil.set("order"+order.getId(),order);
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.update(order);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean deleteOrder(OrderEntity order) {
        orderrepository.delete(order);
        redisUtil.del("book"+order.getId());
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.delete(order);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    @Transactional
//    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public boolean addOrderUseTX(OrderEntity order) {
        orderrepository.addOrderUseTX(order.getId(),order.getUser().getId(),order.getOrder_time());
        //        int result=10/0;
        return true;
    }

    @Override
    public OrderEntity findOrderById(Integer id) {
        OrderEntity order=(OrderEntity) redisUtil.get("order"+id);
        if (order==null)
        {
            order=orderrepository.findOrderById(id);
            redisUtil.set("order"+order.getId(),order);
        }
        return order;
    }


    @Override
    public List<OrderEntity> findOrderByExample(OrderEntity order) {
        try {
            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(OrderEntity.class);
            criteria.add(Example.create(order));
            List<OrderEntity> orderdata = criteria.list();
            session.getTransaction().commit();
            return orderdata;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderEntity> findallOrder() {
        return orderrepository.findallOrder();
    }

    @Override
    public Integer findmaxOrderid() {
        return orderrepository.findmaxOrderid();
    }
}
