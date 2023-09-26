package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.entities.EmployeeDetails;

@Repository

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, String> {
	
	public EmployeeDetails findBySapId(Long sapId);
	public EmployeeDetails getBySapId(Long sapId);
	

}
