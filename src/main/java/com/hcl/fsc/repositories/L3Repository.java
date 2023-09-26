package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.GraduationSpecialization;
import com.hcl.fsc.mastertables.L3;

@Repository
public interface L3Repository extends JpaRepository<L3, String> {

	public L3 getByKey(String key);

	public L3 findByValue(String value);
	public L3 findTopByOrderByUidDesc();
}
