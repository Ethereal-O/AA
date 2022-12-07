package com.example.bookstore.ServiceImpl;

import com.example.bookstore.Dao.*;
import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookdao;

    @Override
    public List getbookauthorservice(String bookname) {
        List<BookEntity> books=  bookdao.findallBook();
        for (BookEntity book:books)
        {
            if (book.getName().equals(bookname))
            {
                List res=new ArrayList();
                res.add(book.getAuthor());
                return res;
            }
        }
        return null;
    }
}
