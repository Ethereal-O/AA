package com.example.bookstore.DaoImpl;

import com.example.bookstore.Dao.BookDao;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;


    @Override
    public boolean addBook(BookEntity book) {
        book=bookrepository.save(book);
        return true;
    }

    @Override
    public boolean updateBook(BookEntity book) {
        book=bookrepository.save(book);
        return true;
    }

    @Override
    public boolean deleteBook(BookEntity book) {
        bookrepository.delete(book);
        System.out.println("deleting book with id "+book.getId());
        return true;
    }

    @Override
    public BookEntity findBookById(Integer id) {
        return bookrepository.findBookById(id);
    }

    @Override
    public BookEntity findBookByName(String name) {
        return bookrepository.findBookByName(name);
    }


    @Override
    public List<BookEntity> findallBook() {
        return bookrepository.findallBook();
    }
}
