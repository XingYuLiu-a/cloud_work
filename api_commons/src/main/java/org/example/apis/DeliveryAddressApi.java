package org.example.apis;

import org.example.pojo.DeliveryAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "address-service")
public interface DeliveryAddressApi {
    @GetMapping("/address/{daId}")
    DeliveryAddress getById(@PathVariable("daId") Integer daId);
}
