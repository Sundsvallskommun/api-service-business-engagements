package se.sundsvall.businessengagements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import se.sundsvall.dept44.ServiceApplication;

@ServiceApplication
@ConfigurationPropertiesScan("se.sundsvall.businessengagements")
@EnableFeignClients
@EnableScheduling
@EnableCaching
public class BusinessEngagements {

	public static void main(String[] args) {
		SpringApplication.run(BusinessEngagements.class, args);
	}

}
