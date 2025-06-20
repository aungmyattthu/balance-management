package com.amt.online.balance_management.controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amt.online.balance_management.model.entity.District;
import com.amt.online.balance_management.model.entity.Township;
import com.amt.online.balance_management.service.LocationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("member/location")
@RequiredArgsConstructor
public class MemberLocationApi {
	
	private final LocationService service;
	
	@GetMapping("district")
	List<District> findDistrict(@RequestParam int regionId){
		
		return service.findDistrictByRegion(regionId);
	}
	
	@GetMapping("township")
	List<Township> findTownship(@RequestParam int districtId){
		
		return service.findTownshipByDistrict(districtId);
	}
}
