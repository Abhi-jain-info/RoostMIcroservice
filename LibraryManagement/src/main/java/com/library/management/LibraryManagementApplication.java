package com.library.management;

import com.library.management.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import static com.library.management.constants.DataConstant.*;

@EnableEurekaClient
@SpringBootApplication
public class LibraryManagementApplication {

	@Autowired
	BorrowerService borrowerService;
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}



	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	/**
	 * code to be executed just after services started
	 * you can test service method here by direct calling
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println(multiplyString("*",192));
		//borrowerService.verifyBorrower("s1");
	}

	public String multiplyString(String s,int n){
		String r = s;
		for(int i=1 ; i<n ;i++){
			r = r.concat(s);
		}
		return r;
	}
}
