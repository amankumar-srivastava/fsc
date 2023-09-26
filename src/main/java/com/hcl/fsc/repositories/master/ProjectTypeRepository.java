package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.ProjectL4;
import com.hcl.fsc.mastertables.ProjectType;

@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer> {
	
	public ProjectType findTopByOrderByUidDesc();

	public boolean existsProjectTypeByprojectType(String projectType);
	
	public ProjectType findByprojectType(String projectType);
}
