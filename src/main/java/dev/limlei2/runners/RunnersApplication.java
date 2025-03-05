package dev.limlei2.runners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RunnersApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RunnersApplication.class, args);

		WelcomeMessage welcomeMessage = (WelcomeMessage) context.getBean("welcomeMessage");


	}

}
