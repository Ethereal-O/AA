package com.example.bookstore.Repository;

import com.example.bookstore.Entity.BookEntity;
import com.example.bookstore.solr.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Integer> {
    @Query(value = "from BookEntity where id=:id")
    public BookEntity findBookById(Integer id);
    @Query(value = "from BookEntity where name=:name")
    public BookEntity findBookByName(String name);
    @Query(value = "from BookEntity")
    public List<BookEntity> findallBook();

    @Query(value = "from BookEntity where type=:type")
    public List<BookEntity> findByType(String type);
}
