package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.L1;

@Repository
public interface L1Repository extends JpaRepository<L1, String> {
	
	public L1 getByKey(String key);
	public L1 findByValue(String value);



}
