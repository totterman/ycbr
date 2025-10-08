package fi.smartbass.ycbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class BffServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BffServerApplication.class, args);
	}

}
