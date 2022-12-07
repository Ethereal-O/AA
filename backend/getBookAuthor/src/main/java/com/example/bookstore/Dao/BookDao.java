package com.example.bookstore.Dao;

import com.example.bookstore.Entity.BookEntity;

import java.util.List;

public interface BookDao {
    public boolean addBook(BookEntity book);
    public boolean updateBook(BookEntity book);
    public boolean deleteBook(BookEntity book);
    //查询
    //根据唯一标识查询单个实体
    public BookEntity findBookById(Integer id);
    public BookEntity findBookByName(String name);

    //得到所有实体
    public List<BookEntity> findallBook();
}
