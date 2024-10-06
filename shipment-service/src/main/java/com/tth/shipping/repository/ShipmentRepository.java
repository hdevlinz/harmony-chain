package com.tth.shipping.repository;

import com.tth.shipping.entity.Shipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, String> {
}
