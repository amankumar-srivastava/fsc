package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.fsc.mastertables.ProjectL4;
import com.hcl.fsc.mastertables.Rm;

public interface RmRepository extends JpaRepository<Rm, Integer>{
	
	public Rm findTopByOrderByUidDesc();

	public boolean existsRmBysapId(String sapId);

	public boolean existsRmByrmName(String rmName);
}
