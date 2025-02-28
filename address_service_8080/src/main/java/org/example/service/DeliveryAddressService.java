package org.example.service;


import org.example.pojo.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {
    List<DeliveryAddress> list(Integer userId);

    void addAddress(DeliveryAddress deliveryAddress);

    void deleteAddress(Integer daId);
    void updateAddress(DeliveryAddress deliveryAddress);

    DeliveryAddress getByDaId(Integer daId);

}
