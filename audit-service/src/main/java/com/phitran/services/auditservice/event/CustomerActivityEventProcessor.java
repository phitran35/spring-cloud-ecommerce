package com.phitran.services.auditservice.event;

import com.phitran.services.auditservice.model.CustomerActivity;
import com.phitran.services.auditservice.repository.CustomerActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

public class CustomerActivityEventProcessor {
    private final CustomerActivityRepository activityRepository;

    @Autowired
    public CustomerActivityEventProcessor(CustomerActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @StreamListener(Sink.INPUT)
    public void processCustomerEvent(CustomerActivity customerActivity) {
        activityRepository.save(customerActivity);
    }
}
