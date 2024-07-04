package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //启用feign客户端
public class BusinessApplication8005 {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication8005.class,args);
    }
}
