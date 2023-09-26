package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.HrL4;
import com.hcl.fsc.mastertables.ProjectCode;

@Repository
public interface HrL4Repository extends JpaRepository<HrL4, Integer> {
	
	public HrL4 findTopByOrderByUidDesc();

	public boolean existsHrL4ByhrL4Code(String hrL4Code);

	public boolean existsHrL4ByhrL4Name(String hrL4Name);
}
