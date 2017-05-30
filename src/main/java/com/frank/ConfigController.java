package com.frank;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

	@RequestMapping("/config/coreos1.yml")
	public String get() {
		return CloudBootApplication.cloudFile;

	}
}
