package com.example.bookstore.Dao;

import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.Entity.UserEntity;

import java.util.List;

public interface BookDao {
    public boolean addBook(BookEntity book);
    public boolean updateBook(BookEntity book);
    public boolean deleteBook(BookEntity book);
    //查询
    //根据唯一标识查询单个实体
    public BookEntity findBookById(Integer id);
    public BookEntity findBookByName(String name);
    //根据实体的条件查询多个实体
    public List<BookEntity> findBookByExample(BookEntity book);
    //得到所有实体
    public List<BookEntity> findallBook();
}
