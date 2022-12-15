package com.example.bookstore.Repository;

import com.example.bookstore.Entity.BookTypeEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookTypeRepository extends Neo4jRepository<BookTypeEntity, Long> {

    BookTypeEntity findByType(String type);

    @Query("MATCH (n:BookTypeEntity)-[r:SIMILAR]-(m:BookTypeEntity) WHERE n.type=$type RETURN m")
    List<BookTypeEntity> findBySimilarBooksType(String type);
}