package com.phitran.services.auditservice.repository;

import com.phitran.services.auditservice.model.CustomerActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "customer-activities", path = "customer-activities")
public interface CustomerActivityRepository extends MongoRepository<CustomerActivity, String> {
}
