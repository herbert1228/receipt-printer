package com.pinnacle.receipt.repository;

import com.pinnacle.receipt.model.Tax;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaxRepository extends MongoRepository<Tax, String> {

  Tax findByLocation(String location);
}
