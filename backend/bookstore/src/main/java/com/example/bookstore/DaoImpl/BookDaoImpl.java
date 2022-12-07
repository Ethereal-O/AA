package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.BookDao;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Redis.RedisUtil;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean addBook(BookEntity book) {
        book=bookrepository.save(book);
        redisUtil.set("book"+book.getId(),book);
        System.out.println("adding book with id "+book.getId());
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.save(book);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean updateBook(BookEntity book) {
        book=bookrepository.save(book);
        redisUtil.set("book"+book.getId(),book);
        System.out.println("updating book with id "+book.getId());
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.update(book);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public boolean deleteBook(BookEntity book) {
        bookrepository.delete(book);
        redisUtil.del("book"+book.getId());
        System.out.println("deleting book with id "+book.getId());
        return true;
//        try {
//            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
//            session.beginTransaction();
//            session.delete(book);
//            session.getTransaction().commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Override
    public BookEntity findBookById(Integer id) {
        BookEntity book=(BookEntity) redisUtil.get("book"+id);
        if (book==null)
        {
            System.out.println("can't find book "+id+" in redis");
            book=bookrepository.findBookById(id);
            redisUtil.set("book"+book.getId(),book);
        }else {
            System.out.println("find book "+book.getId()+" in redis");
        }
        return book;
    }

    @Override
    public BookEntity findBookByName(String name) {
        return bookrepository.findBookByName(name);
    }

    @Override
    public List<BookEntity> findBookByExample(BookEntity book) {
        try {
            Session session= HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(BookEntity.class);
            criteria.add(Example.create(book));
            List<BookEntity> bookdata = criteria.list();
            session.getTransaction().commit();
            return bookdata;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BookEntity> findallBook() {
        return bookrepository.findallBook();
    }
}
