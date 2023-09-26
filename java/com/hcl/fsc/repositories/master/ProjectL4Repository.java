package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.fsc.mastertables.HrL4;
import com.hcl.fsc.mastertables.ProjectL4;

public interface ProjectL4Repository extends JpaRepository<ProjectL4, Integer>{
	
	public ProjectL4 findTopByOrderByUidDesc();

	public boolean existsProjectL4ByprojectL4Code(String projectL4Code);

	public boolean existsProjectL4ByprojectL4Name(String projectL4Name);
}
