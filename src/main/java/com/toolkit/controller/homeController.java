package com.toolkit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class homeController<HttpServletRequest, HttpServletResponse> {
	@RequestMapping(value = {"","/"}, method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response) {
		return "/TXTregex/regex";
	}
}
