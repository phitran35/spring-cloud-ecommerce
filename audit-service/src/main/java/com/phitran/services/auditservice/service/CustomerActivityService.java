package com.phitran.services.auditservice.service;

import com.phitran.services.auditservice.domain.CustomerActivity;
import com.phitran.services.auditservice.repository.CustomerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerActivityService {
    private final CustomerActivityRepository repository;

    @Autowired
    public CustomerActivityService(CustomerActivityRepository repository) {
        this.repository = repository;
    }

    /**
     * find all activities
     * @return
     */
    public List<CustomerActivity> findAll() {
        ArrayList<CustomerActivity> result = new ArrayList<>();
        Iterable<CustomerActivity> activities  = repository.findAll();
        activities.forEach(result::add);
        return result;
    }

    /**
     * find all by customer username
     * @param username
     * @return
     */
    public List<CustomerActivity> findAllByUsername(String username) {
        return repository.findByUsername(username);
    }

}
