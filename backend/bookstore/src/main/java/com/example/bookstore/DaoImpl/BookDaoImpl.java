package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.BookDao;
import com.example.bookstore.Entity.BookDescriptionEntity;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Redis.RedisUtil;
import com.example.bookstore.Repository.BookDescriptionRepository;
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
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BookDescriptionRepository bookDescriptionRepository;

    @Override
    public boolean addBook(BookEntity book) {
        book=bookrepository.save(book);
        redisUtil.set("book"+book.getId(),book);
        System.out.println("adding book with id "+book.getId());


        // add from mongodb
        BookDescriptionEntity bookDescription=new BookDescriptionEntity(book.getId(), book.getDescription());
        bookDescriptionRepository.save(bookDescription);



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

        // update from mongodb
        BookDescriptionEntity bookDescription=new BookDescriptionEntity(book.getId(), book.getDescription());
        bookDescriptionRepository.save(bookDescription);


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

        // delete from mongodb
        bookDescriptionRepository.deleteById(book.getId());
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

        // find from mongdb
        Optional<BookDescriptionEntity> bookDescription = bookDescriptionRepository.findById(id);
        if (bookDescription.isPresent()){
            book.setDescription(bookDescription.get().getDescription());
        }
        else{
            book.setDescription("");
        }

        return book;
    }

    @Override
    public BookEntity findBookByName(String name) {

        BookEntity book = bookrepository.findBookByName(name);


        // find from mongdb
        Optional<BookDescriptionEntity> bookDescription = bookDescriptionRepository.findById(book.getId());
        if (bookDescription.isPresent()){
            book.setDescription(bookDescription.get().getDescription());
        }
        else{
            book.setDescription("");
        }

        return book;
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
        List<BookEntity>books= bookrepository.findallBook();


        // find from mongdb
        for (BookEntity book:books)
        {
            Optional<BookDescriptionEntity> bookDescription = bookDescriptionRepository.findById(book.getId());
            if (bookDescription.isPresent()){
                book.setDescription(bookDescription.get().getDescription());
            }
            else{
                book.setDescription("");
            }

        }

        return books;
    }
}
