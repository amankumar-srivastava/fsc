package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.CollegeTiering;
import com.hcl.fsc.mastertables.L2;

@Repository
public interface L2Repository extends JpaRepository<L2, String> {

	public L2 getByKey(String key);

	public L2 findByValue(String value);
	public L2 findTopByOrderByUidDesc();
}
