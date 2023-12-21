package se.sundsvall.businessengagements;

import static org.springframework.boot.SpringApplication.run;

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
	public static void main(String... args) {
		run(BusinessEngagements.class, args);
	}
}
