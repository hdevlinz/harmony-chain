package com.tth.rating.repository;

import com.tth.rating.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    Optional<Rating> findByUserIdAndSupplierId(String userId, String supplierId);

}
