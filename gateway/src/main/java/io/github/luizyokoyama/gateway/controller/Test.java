package io.github.luizyokoyama.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {

	@GetMapping("/")
	public String home() {


		return "test";
	}
}
