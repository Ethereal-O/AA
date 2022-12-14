package com.example.bookstore.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
public class BookDescriptionEntity {
    @Id
    private int id;

    private String description;

    public BookDescriptionEntity(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
