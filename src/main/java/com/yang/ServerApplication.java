package com.yang;

import com.yang.thrift.ClientHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.yang.dao")
@EnableScheduling
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		ClientHelper clientHelper = context.getBean(ClientHelper.class);
		clientHelper.initClients();
	}

}
