package com.amt.online.balance_management.model.repo;

import java.util.List;

import com.amt.online.balance_management.model.BaseRepository;
import com.amt.online.balance_management.model.entity.District;

public interface DistrictRepo extends BaseRepository<District, Integer> {

	List<District> findByRegionId(int regionId);

}
