package com.hcl.fsc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.entities.EmployeeProjectDetails;

@Repository	
public interface EmployeeProjectDetailRepository extends JpaRepository<EmployeeProjectDetails, Long> {
	public List<EmployeeProjectDetails> findAllByProjUid(Integer projUid);
}
