package com.amt.online.balance_management.controller.member;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amt.online.balance_management.controller.member.dto.ProfileEditForm;
import com.amt.online.balance_management.model.entity.District;
import com.amt.online.balance_management.model.entity.Region;
import com.amt.online.balance_management.model.entity.Township;
import com.amt.online.balance_management.service.LocationService;
import com.amt.online.balance_management.service.MemberProfileService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("member/profile")
public class MemberSettingProfileController {

	private final LocationService locationService;
	
	private final MemberProfileService profileService;
	
	private final 
	
	@GetMapping
	String editProfile() {
		return "member/profile/edit";
	}
	
	@PostMapping
	String updateProfile(
			@Validated @ModelAttribute(name = "form") ProfileEditForm editForm, BindingResult result){
		if(result.hasErrors()) {
			return "member/profile/edit";
		}
		var username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		profileService.save(username, editForm);
		
		return "redirect:/member/home";
	}
	
	@PostMapping("photo")
	String uploadPhoto(@RequestParam MultipartFile file,
							HttpServletRequest request) {
		var username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		var imageFolder = request.getServletContext().getRealPath("/resources/photos");
		
		profileService.saveImage(username, imageFolder, file);
		
		System.out.println(file.getOriginalFilename());
		return "redirect:/member/profile";
	}
	
	@ModelAttribute(name = "regions")
	List<Region> getRegions(){
		return locationService.getAllLocations();
	}
	
	@ModelAttribute(name = "districts")
	List<District> getDistricts(@ModelAttribute(name = "form") ProfileEditForm editForm){
		return locationService.getDistrictForSelectedTownship(editForm.getTownship());
	}
	
	@ModelAttribute(name = "townships")
	List<Township> getTownships(@ModelAttribute(name = "form") ProfileEditForm editForm){
		return locationService.getTownshipsForSelectedTownship(editForm.getTownship());
	}
	
	@ModelAttribute(name = "form")
	ProfileEditForm editForm() {
		var username = SecurityContextHolder
								.getContext()
								.getAuthentication()
								.getName();
		return profileService.getEditForm(username);
	}
	
}
