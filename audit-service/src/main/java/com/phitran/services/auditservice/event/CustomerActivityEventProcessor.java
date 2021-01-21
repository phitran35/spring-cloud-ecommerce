package com.phitran.services.auditservice.event;

import com.phitran.services.auditservice.domain.CustomerActivity;
import com.phitran.services.auditservice.repository.CustomerActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class CustomerActivityEventProcessor {
    private final CustomerActivityRepository activityRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerActivityEventProcessor.class);

    @Autowired
    public CustomerActivityEventProcessor(CustomerActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @StreamListener(Sink.INPUT)
    public void processCustomerEvent(CustomerActivity customerActivity) {
        LOGGER.info("CustomerActivity audit processing {}", customerActivity);
        activityRepository.save(customerActivity);
    }
}
