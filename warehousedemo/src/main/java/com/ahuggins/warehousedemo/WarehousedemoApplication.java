package com.ahuggins.warehousedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WarehousedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousedemoApplication.class, args);
	}

}
