package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.entities.ProjectAssignmentHistory;

import java.util.List;

@Repository
public interface ProjectAssignmentHistoryRepository extends JpaRepository<ProjectAssignmentHistory, Integer> {

	@Query(value = "Select * From Project_assignment_history Where empSAPID=?", nativeQuery = true)
	public List<ProjectAssignmentHistory> getEmployeeAssignedProjectHistoriesByECode(int eCode);

}