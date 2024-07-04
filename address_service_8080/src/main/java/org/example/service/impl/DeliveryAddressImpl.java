package org.example.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.mapper.DeliveryAddressMapper;
import org.example.pojo.DeliveryAddress;
import org.example.service.DeliveryAddressService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryAddressImpl implements DeliveryAddressService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryAddressImpl.class);
    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Override
    @CircuitBreaker(name = "address-service", fallbackMethod = "defaultList")
    public List<DeliveryAddress> list(Integer userId) {
        return deliveryAddressMapper.list(userId);
    }

    @Override
    @CircuitBreaker(name = "address-service", fallbackMethod = "defaultList")
    public void addAddress(DeliveryAddress deliveryAddress) {
        deliveryAddressMapper.addAddress(deliveryAddress);
    }

    @Override
    @CircuitBreaker(name = "address-service", fallbackMethod = "defaultList")
    public void deleteAddress(Integer daId) {
        deliveryAddressMapper.deleteAddress(daId);
    }

    @Override
    @CircuitBreaker(name = "address-service", fallbackMethod = "defaultList")
    public void updateAddress(DeliveryAddress deliveryAddress) {
        deliveryAddressMapper.updateAddress(deliveryAddress);
    }

    @Override
    @CircuitBreaker(name = "address-service", fallbackMethod = "defaultList")
    public DeliveryAddress getByDaId(Integer daId) {
        return deliveryAddressMapper.getByDaId(daId);
    }


    // Fallback methods

    public List<DeliveryAddress> defaultList(Integer userId, Throwable throwable) {
        logger.error("Error in list, userId: {}, error: {}", userId, throwable.getMessage());

        return null;
    }

    public void defaultAddAddress(DeliveryAddress deliveryAddress, Throwable throwable) {
        logger.error("Error in addAddress, deliveryAddress: {}, error: {}", deliveryAddress, throwable.getMessage());
    }

    public void defaultDeleteAddress(Integer daId, Throwable throwable) {
        logger.error("Error in deleteAddress, daId: {}, error: {}", daId, throwable.getMessage());
    }

    public void defaultUpdateAddress(DeliveryAddress deliveryAddress, Throwable throwable) {
        logger.error("Error in updateAddress, deliveryAddress: {}, error: {}", deliveryAddress, throwable.getMessage());
    }

    public DeliveryAddress defaultGetByDaId(Integer daId, Throwable throwable) {
        logger.error("Error in getByDaId, daId: {}, error: {}", daId, throwable.getMessage());

        return null;
    }
}
