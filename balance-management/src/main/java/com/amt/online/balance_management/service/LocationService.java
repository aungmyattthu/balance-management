package com.amt.online.balance_management.service;

import static com.amt.online.balance_management.utils.EntityOperations.safeCall;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amt.online.balance_management.model.entity.District;
import com.amt.online.balance_management.model.entity.Region;
import com.amt.online.balance_management.model.entity.Township;
import com.amt.online.balance_management.model.repo.DistrictRepo;
import com.amt.online.balance_management.model.repo.RegionRepo;
import com.amt.online.balance_management.model.repo.TownshipRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

	private final RegionRepo regionRepo;
	private final DistrictRepo districtRepo;
	private final TownshipRepo townshipRepo;
	
	public List<Region> getAllLocations() {
		
		return regionRepo.findAll();
	}

	public List<District> findDistrictByRegion(int regionId) {
		// TODO Auto-generated method stub
		return districtRepo.findByRegionId(regionId);
	}

	public List<Township> findTownshipByDistrict(int districtId) {
		// TODO Auto-generated method stub
		return townshipRepo.findByDistrictId(districtId);
	}

	public List<District> getDistrictForSelectedTownship(Integer townshipId) {
		if(null != townshipId) {
			var township = safeCall(townshipRepo.findById(townshipId), "township", "id", townshipId);
		
			var regionId = township.getDistrict().getRegion().getId();
			
			return districtRepo.findByRegionId(regionId);
		}
		return null;
	}

	public List<Township> getTownshipsForSelectedTownship(Integer townshipId) {
		
		if(null != townshipId) {
			
			var township = safeCall(townshipRepo.findById(townshipId), "township", "id", townshipId);
			var districtId = township.getDistrict().getId();
			
			return townshipRepo.findByDistrictId(districtId);
		}
		return null;
	}
	
}
