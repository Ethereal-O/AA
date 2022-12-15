package com.example.bookstore.Entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class BookTypeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private BookTypeEntity() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

    public BookTypeEntity(String type) {
        this.type = type;
    }

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "SIMILAR")
    public Set<BookTypeEntity> similarBooks;

    public void addRelationType(BookTypeEntity bookType) {
        if (similarBooks == null) {
            similarBooks = new HashSet<>();
        }
        similarBooks.add(bookType);
    }

    public String getType(){return type;}

    public void setType(String type) {
        this.type = type;
    }
}
