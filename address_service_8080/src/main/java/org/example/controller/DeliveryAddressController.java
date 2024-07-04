package org.example.controller;


import org.example.pojo.DeliveryAddress;
import org.example.resp.ResultData;
import org.example.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class DeliveryAddressController {
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @GetMapping("/userId/{userId}")
    public ResultData list(@PathVariable("userId") Integer userId){
        List<DeliveryAddress> deliveryAddressList = deliveryAddressService.list(userId);

        return ResultData.success(deliveryAddressList);
    }
    @PostMapping("/")
    public ResultData addAddress(@RequestBody DeliveryAddress deliveryAddress){
        deliveryAddressService.addAddress(deliveryAddress);
        return ResultData.success(null);
    }
    @DeleteMapping("/{daId}")
    public ResultData deleteAddress(@PathVariable("daId") Integer daId){
        deliveryAddressService.deleteAddress(daId);
        return ResultData.success(null);
    }
    @PutMapping("/")
    public ResultData updateAddress(@RequestBody DeliveryAddress deliveryAddress){
        deliveryAddressService.updateAddress(deliveryAddress);
        return ResultData.success(null);
    }
    @GetMapping("/daId/{daId}")
    public ResultData getByDaId(@PathVariable("daId") Integer daId){
        DeliveryAddress deliveryAddress = deliveryAddressService.getByDaId(daId);

        return ResultData.success(deliveryAddress);
    }
    @GetMapping("/{daId}")
    public DeliveryAddress getById(@PathVariable("daId") Integer daId){
        DeliveryAddress deliveryAddress = deliveryAddressService.getByDaId(daId);

        return deliveryAddress;
    }
}
