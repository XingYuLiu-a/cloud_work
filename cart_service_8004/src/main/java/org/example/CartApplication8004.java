package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //启用feign客户端
public class CartApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication8004.class,args);
    }
}
