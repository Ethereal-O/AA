package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.BookDao;
import com.example.bookstore.Entity.BookDescriptionEntity;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Entity.BookTypeEntity;
import com.example.bookstore.Redis.RedisUtil;
import com.example.bookstore.Repository.BookDescriptionRepository;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.BookTypeRepository;
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
import java.util.Arrays;
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
    @Autowired
    private BookTypeRepository bookTypeRepository;

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
    public List<BookEntity> findBookByType(String type) {
        List<BookEntity> books=bookrepository.findByType(type);

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

    @Override
    public boolean addbooktypedata() {
        bookTypeRepository.deleteAll();

        // novels
        BookTypeEntity fantasyNovel = new BookTypeEntity("魔幻小说");
        BookTypeEntity scienceNovel = new BookTypeEntity("科幻小说");
        BookTypeEntity kungFuNovel = new BookTypeEntity("武侠小说");
        BookTypeEntity socialNovel = new BookTypeEntity("社会小说");
        BookTypeEntity mysteryNovel = new BookTypeEntity("悬疑/推理小说");

        bookTypeRepository.save(fantasyNovel);
        bookTypeRepository.save(scienceNovel);
        bookTypeRepository.save(kungFuNovel);
        bookTypeRepository.save(socialNovel);
        bookTypeRepository.save(mysteryNovel);

        fantasyNovel=bookTypeRepository.findByType(fantasyNovel.getType());
        fantasyNovel.addRelationType(scienceNovel);
        fantasyNovel.addRelationType(kungFuNovel);
        fantasyNovel.addRelationType(socialNovel);
        fantasyNovel.addRelationType(mysteryNovel);
        bookTypeRepository.save(fantasyNovel);

        scienceNovel=bookTypeRepository.findByType(scienceNovel.getType());
        scienceNovel.addRelationType(kungFuNovel);
        scienceNovel.addRelationType(socialNovel);
        scienceNovel.addRelationType(mysteryNovel);
        bookTypeRepository.save(scienceNovel);

        kungFuNovel=bookTypeRepository.findByType(kungFuNovel.getType());
        kungFuNovel.addRelationType(socialNovel);
        kungFuNovel.addRelationType(mysteryNovel);
        bookTypeRepository.save(kungFuNovel);

        socialNovel=bookTypeRepository.findByType(socialNovel.getType());
        socialNovel.addRelationType(mysteryNovel);
        bookTypeRepository.save(socialNovel);

        // literatures
        BookTypeEntity youthLiteratures = new BookTypeEntity("青春文学");
        BookTypeEntity bioLiteratures = new BookTypeEntity("传记文学");
        BookTypeEntity childLiteratures = new BookTypeEntity("儿童文学");

        bookTypeRepository.save(youthLiteratures);
        bookTypeRepository.save(bioLiteratures);
        bookTypeRepository.save(childLiteratures);

        youthLiteratures=bookTypeRepository.findByType(youthLiteratures.getType());
        youthLiteratures.addRelationType(bioLiteratures);
        youthLiteratures.addRelationType(childLiteratures);
        bookTypeRepository.save(youthLiteratures);

        bioLiteratures=bookTypeRepository.findByType(bioLiteratures.getType());
        bioLiteratures.addRelationType(childLiteratures);
        bookTypeRepository.save(bioLiteratures);

        // others
        BookTypeEntity programType = new BookTypeEntity("编程");
        BookTypeEntity masterType = new BookTypeEntity("世界名著");
        BookTypeEntity referType = new BookTypeEntity("中小学教辅");
        BookTypeEntity ancientType = new BookTypeEntity("古籍");

        bookTypeRepository.save(programType);
        bookTypeRepository.save(masterType);
        bookTypeRepository.save(referType);
        bookTypeRepository.save(ancientType);

        return true;
    }

    @Override
    public List<BookTypeEntity> searchsimilars(String type) {
        List<BookTypeEntity> similars=bookTypeRepository.findBySimilarBooksType(type);
        similars.add(bookTypeRepository.findByType(type));
        return similars;
    }
}
