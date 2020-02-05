package com.desafio.source.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source")
public class SourceController {

	@RequestMapping(method=RequestMethod.GET)
	public String source () {
		return "https://github.com/leandrow3b/DesafioSoftPlan";
	}
}
