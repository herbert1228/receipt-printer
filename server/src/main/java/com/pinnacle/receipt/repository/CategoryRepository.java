package com.pinnacle.receipt.repository;

import com.pinnacle.receipt.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

  Category findByName(String name);
}
