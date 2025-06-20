package com.amt.online.balance_management.model.repo;

import java.util.List;

import com.amt.online.balance_management.model.BaseRepository;
import com.amt.online.balance_management.model.entity.Township;

public interface TownshipRepo extends BaseRepository<Township, Integer>{

	List<Township> findByDistrictId(int districtId);

}
