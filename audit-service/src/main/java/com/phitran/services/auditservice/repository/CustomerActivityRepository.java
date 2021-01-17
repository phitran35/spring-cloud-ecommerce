package com.phitran.services.auditservice.repository;

import com.phitran.services.auditservice.domain.CustomerActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerActivityRepository extends CrudRepository<CustomerActivity, String> {
    List<CustomerActivity> findByUsername(String username);
}
