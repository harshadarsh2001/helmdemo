package com.harshproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/data")
	String getMessage() {
		return "This is message from pod";
	}
}
