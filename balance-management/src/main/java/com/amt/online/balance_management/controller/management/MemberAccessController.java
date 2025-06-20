package com.amt.online.balance_management.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amt.online.balance_management.controller.member.dto.AccessHistorySearch;
import com.amt.online.balance_management.service.AccessHistoryService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller("adminMemberAccessController")
@RequestMapping("admin/access")
public class MemberAccessController {
	
	private final AccessHistoryService service;
	
	@GetMapping
	String search(ModelMap model,
			AccessHistorySearch form, 
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "10") int size) {
		
		var result = service.search(form, page, size);
		model.put("result", result);
		
		return "management/access/list";
	}
}
