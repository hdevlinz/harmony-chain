package com.tth.shipping.repository;

import com.tth.shipping.entity.DeliverySchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryScheduleRepository extends MongoRepository<DeliverySchedule, String> {
}
