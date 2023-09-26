package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.ProjectCategory;
import com.hcl.fsc.mastertables.ProjectCode;

@Repository
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Integer> {
	public ProjectCategory findTopByOrderByUidDesc();

	public boolean existsProjectCategoryByprojectCategory(String projectCode);
}