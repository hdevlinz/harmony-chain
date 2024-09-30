package com.tth.rating.repository;

import com.tth.rating.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    Page<Rating> findAllBySupplierId(String supplierId, Pageable pageable);

}
