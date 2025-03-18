package site.nansan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NansanApplication {

	public static void main(String[] args) {
		SpringApplication.run(NansanApplication.class, args);
	}

}
