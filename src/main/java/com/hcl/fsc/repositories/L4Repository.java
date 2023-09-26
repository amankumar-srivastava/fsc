package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.GraduationSpecialization;
import com.hcl.fsc.mastertables.L4;

@Repository
public interface L4Repository extends JpaRepository<L4, String> {

	public L4 getByKey(String key);

	public L4 findByValue(String value);
	public L4 findTopByOrderByUidDesc();
}
