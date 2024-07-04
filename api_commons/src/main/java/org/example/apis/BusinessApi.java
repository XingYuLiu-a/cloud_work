package org.example.apis;

import org.example.pojo.Business;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "business-service")
public interface BusinessApi {
    @GetMapping("/business/{businessId}")
    Business getById(@PathVariable("businessId") Integer businessId);
}
