package com.datasoft.bkash.ea;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableBatchProcessing
public class EAPortalApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(EAPortalApplication.class, args);
	}

}
