package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.CartDao;
import com.example.bookstore.Entity.CartEntity;
import com.example.bookstore.Entity.UserEntity;
import com.example.bookstore.Repository.CartRepository;
import com.example.bookstore.Utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    private CartRepository cartrepository;
    @Override
    public boolean addCart(CartEntity cart) {
        cartrepository.save(cart);
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.save(cart);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean updateCart(CartEntity cart) {
        cartrepository.save(cart);
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.update(cart);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean deleteCart(CartEntity cart) {
        cartrepository.delete(cart);
//        cartrepository.deleteById(cart.getId());
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.delete(cart);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean deleteCartByIdUseTX(Integer id) {
        cartrepository.deleteCartById(id);
        return false;
    }

    @Override
    public CartEntity findCartById(Integer id) {
        return cartrepository.findCartById(id);
    }

    @Override
    public List<CartEntity> findCartsByBookId(Integer bookid) {
        return cartrepository.findCartsByBookId(bookid);
    }

    @Override
    public List<CartEntity> findCartByExample(CartEntity cart) {
        try {
            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CartEntity.class);
            criteria.add(Example.create(cart));
            List<CartEntity> cartdata = criteria.list();
            session.getTransaction().commit();
            return cartdata;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CartEntity> findallCart() {
        return cartrepository.findallCart();
    }
}
