package com.example.bookstore.Repository;

import com.example.bookstore.Entity.BookDescriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDescriptionRepository extends MongoRepository<BookDescriptionEntity, Integer> {
}
