package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

	public Region getByKey(String key);

	public Region findByValue(String value);

}
