package org.example.controller;


import org.example.pojo.Business;
import org.example.resp.ResultData;
import org.example.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @GetMapping("/")
    public ResultData list(){
        List<Business> bussinessList = businessService.list();

        return ResultData.success(bussinessList);
    }
    @GetMapping("/search/{name}")
    public ResultData search(@PathVariable("name") String  name){
        List<Business> businessList = businessService.search(name);

        return ResultData.success(businessList);
    }
    @GetMapping("/orderTypeId/{orderTypeId}")
    public ResultData getByOrderTypeId(@PathVariable("orderTypeId") Integer orderTypeId){
        List<Business> businessList = businessService.getByOrderTypeId(orderTypeId);

        return ResultData.success(businessList);
    }

    @GetMapping("/businessId/{businessId}")
    public ResultData getByBusinessId(@PathVariable("businessId") Integer businessId){
        Business business = businessService.getByBusinessId(businessId);

        return ResultData.success(business);
    }

    @PostMapping("/")
    public ResultData add(@RequestBody Business business) {
        businessService.add(business);

        return ResultData.success(null);
    }
    @DeleteMapping("/{businessId}")
    public ResultData delete(@PathVariable("businessId") Integer businessId){
        businessService.delete(businessId);
        return ResultData.success(null);
    }

    @GetMapping("/total/{businessId}/{userId}")
    public ResultData total(@PathVariable("businessId") Integer businessId,@PathVariable("userId") Integer userId){
        Double total =  businessService.total(businessId,userId);
        if (total == -1.0){
            return ResultData.fail(201,"系统繁忙,请稍后再试");
        }
        return ResultData.success(total);
    }
    @GetMapping("/quantity/{businessId}/{userId}")
    public ResultData quantity(@PathVariable("businessId") Integer businessId,@PathVariable("userId") Integer userId){
        Integer quantity = businessService.getQuantity(businessId,userId);
        if (quantity == -1){
            return ResultData.fail(201,"系统繁忙,请稍后再试");
        }
        return ResultData.success(quantity);
    }



    @GetMapping("/{businessId}")
    public Business getById(@PathVariable("businessId") Integer businessId){
        Business business = businessService.getByBusinessId(businessId);

        return business;
    }


}
