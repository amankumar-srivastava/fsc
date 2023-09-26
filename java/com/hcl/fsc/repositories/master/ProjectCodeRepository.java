package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.ProjectCode;

@Repository
@EnableJpaRepositories
public interface ProjectCodeRepository extends JpaRepository<ProjectCode, Integer>{
	
	public ProjectCode findTopByOrderByUidDesc();
	
	public boolean existsProjectCodeByprojectCode(String projectCode);
	
	public boolean existsProjectCodeByprojectName(String projectName);
	
	public ProjectCode findByProjectCode(String projectCode);
}
