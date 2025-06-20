package com.amt.online.balance_management.controller.anonymous;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amt.online.balance_management.controller.anonymous.dto.SignUpForm;
import com.amt.online.balance_management.service.SignUpService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
	
	
	final private SignUpService signUpService;
	
	@GetMapping
	String index() {
		return "anonymous/signup";
	}

	@PostMapping
	String signUp(
			@Validated @ModelAttribute(name = "form") SignUpForm signUpForm, 
			BindingResult result,
			ModelMap model,
			RedirectAttributes redirectAttributes
			) {
		if (result.hasErrors()) {
			return "anonymous/signup";
		}

		// Sign up business
		try {
			var message = signUpService.signUp(signUpForm);
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/signin";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.put("message", e.getMessage());
			return "anonymous/signup";
		}
	}

	@ModelAttribute(name = "form")
	SignUpForm signUpForm() {
		return new SignUpForm();
	}
}
