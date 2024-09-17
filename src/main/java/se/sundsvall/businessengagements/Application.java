package se.sundsvall.businessengagements;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import se.sundsvall.dept44.ServiceApplication;
import se.sundsvall.dept44.util.jacoco.ExcludeFromJacocoGeneratedCoverageReport;

@ServiceApplication
@ConfigurationPropertiesScan("se.sundsvall.businessengagements")
@EnableFeignClients
@EnableCaching
@ExcludeFromJacocoGeneratedCoverageReport
public class Application {

	public static void main(final String... args) {
		run(Application.class, args);
	}

}
