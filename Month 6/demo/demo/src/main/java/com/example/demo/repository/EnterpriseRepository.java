package com.example.demo.repository;

import com.example.demo.model.Enterprise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseRepository extends MongoRepository<Enterprise, String> {
}
