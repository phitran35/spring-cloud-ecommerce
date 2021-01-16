package com.phitran.services.productservice.aop;

import com.phitran.services.productservice.controller.ProductController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class CustomerAuditAspect {
    private final Source source;

    @Value("${spring.data.web.sort.sort-parameter:sort}")
    private String sortParameter;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAuditAspect.class);

    public CustomerAuditAspect(Source source) {
        this.source = source;
    }

    @Before("execution(* com.phitran.services.productservice.controller.ProductController.getProductDetail(..))")
    @Async
    /**
     * observer which product item user view then sending to MQ for auditing
     * @param JoinPoint
     */
    public void logUserViewProduct(JoinPoint joinPoint) {
        Object[] methodArguments = joinPoint.getArgs();
        String productId = (String) methodArguments[0];
        String username = (String) methodArguments[1];
        CustomerActivity customerActivity = new CustomerActivity(username, LocalDateTime.now(), productId);
        LOGGER.info("Product view detail audit activity {}", customerActivity);
        Message<CustomerActivity> message = MessageBuilder.withPayload(customerActivity).build();
        source.output().send(message);
    }

    @Before("execution(* com.phitran.services.productservice.controller.ProductController.getAllProducts(..))")
    @Async
    /**
     * observer filter options to sending to MQ
     * @param JoinPoint
     */
    public void logUserFilterAndSort(JoinPoint joinPoint) {
        LOGGER.info("Product filtering and sort audit activity {}", joinPoint);
        Object[] methodArguments = joinPoint.getArgs();
        // get filter options
        Map<String, List<String>> requestParams = (Map<String, List<String>>) methodArguments[2];
        List<String> sortBy = requestParams.get(sortParameter);
        Map<String, List<String>> filterBy = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : requestParams.entrySet()) {
            if (!entry.getKey().equals(sortParameter)) {
                filterBy.put(entry.getKey(), entry.getValue());
            }
        }
        // send message payload to MQ
        if ((sortBy != null && !sortBy.isEmpty()) || (!filterBy.isEmpty())) {
            String username = (String) methodArguments[3];
            CustomerActivity customerActivity = new CustomerActivity(username,
                    LocalDateTime.now(), null, filterBy, sortBy);
            LOGGER.info("Filter options and sort auditing {}", customerActivity);
            Message<CustomerActivity> message = MessageBuilder.withPayload(customerActivity).build();
            source.output().send(message);
        }
    }
}
