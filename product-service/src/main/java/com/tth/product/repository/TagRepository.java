package com.tth.product.repository;

import com.tth.product.entity.Tag;
import com.tth.product.repository.specification.TagSpecification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag, String>, TagSpecification {

    Optional<Tag> findByName(String tagName);

}
