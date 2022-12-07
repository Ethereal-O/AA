package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.UserDao;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Entity.OrderEntity;
import com.example.bookstore.Entity.UserEntity;
import com.example.bookstore.Redis.RedisUtil;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.lang.System.out;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userrepository;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean addUser(UserEntity user) {
        userrepository.save(user);
        redisUtil.set("order"+user.getId(),user);
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.save(user);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean updateUser(UserEntity user) {
        userrepository.save(user);
        redisUtil.set("order"+user.getId(),user);
        return true;
//        try {
////            userrepository.save(user);
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.update(user);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean deleteUser(UserEntity user) {
        userrepository.delete(user);
        redisUtil.del("order"+user.getId());
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.delete(user);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public UserEntity findUserById(Integer id) {
        UserEntity user=(UserEntity) redisUtil.get("user"+id);
        if (user==null)
        {
            user=userrepository.findUserById(id);
            redisUtil.set("user"+user.getId(),user);
        }
        return user;
    }

    @Override
    public UserEntity findUserByName(String name) {
        List<UserEntity> userlist=userrepository.findUserByName(name);
        if(userlist.size()==0) return null;
        return userlist.get(0);
    }

    @Override
    public List<UserEntity> findUserByExample(UserEntity user) {
        try {
            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(UserEntity.class);
            criteria.add(Example.create(user));
            List<UserEntity> userdata = criteria.list();
            session.getTransaction().commit();
            return userdata;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserEntity> findallUser() {
        return userrepository.findallUser();
    }
}
