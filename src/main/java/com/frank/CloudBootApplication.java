package com.frank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.frank.CloudUtils;

@SpringBootApplication
public class CloudBootApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${password}")
	String password;

	@Value("${username}")
	String username;

	public static String cloudFile;

	public static void main(String[] args) {
		SpringApplication.run(CloudBootApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		logger.info(password);
		String result = CloudUtils.execCmd("openssl passwd -1 " + password);

		InputStream is = new ClassPathResource("cloudconfig.yml").getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String read = null;
		StringBuffer sb = new StringBuffer();
		while ((read = br.readLine()) != null) {
			sb.append(read + "\n");
		}
		String fileResult = sb.toString();
		cloudFile = fileResult.replace("CLOUD_USERNAME", username).replace("CLOUD_PASSWORD_HASH", result);

		logger.info(cloudFile);
	}

}
