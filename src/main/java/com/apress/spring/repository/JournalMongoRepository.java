package com.apress.spring.repository;

import com.apress.spring.domain.JournalMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalMongoRepository extends MongoRepository<JournalMongo, String> {

    public List<JournalMongo> findByTitleLike(String word);
}
