package com.hiberus.checkout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HiberusCheckoutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiberusCheckoutServiceApplication.class, args);
	}

}
