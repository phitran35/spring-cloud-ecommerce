package com.phitran.services.auditservice.controller;

import com.phitran.services.auditservice.domain.CustomerActivity;
import com.phitran.services.auditservice.service.CustomerActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Api( tags = "Customer Activities")
@RequestMapping("/customer-activities")
public class CustomerActivityController {
    private final CustomerActivityService customerActivityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerActivityController.class);

    @Autowired
    public CustomerActivityController(CustomerActivityService customerActivityService) {
        this.customerActivityService = customerActivityService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "This method is used to get all customer activities.")
    public List<CustomerActivity> getAll() {
        LOGGER.info("CustomerActivity find all");
        return customerActivityService.findAll();
    }

    @GetMapping("/by-username/{username}")
    @ApiOperation(value = "This method is used to get all customer activities.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "string", paramType = "path",
                    value = "Customer's username, ex: test1@mail.com", defaultValue = "test1@mail.com"),
    })
    public List<CustomerActivity> getAllByUsername(@PathVariable @ApiIgnore String username) {
        LOGGER.info("CustomerActivity find by username = {}", username);
        return customerActivityService.findAllByUsername(username);
    }

}
