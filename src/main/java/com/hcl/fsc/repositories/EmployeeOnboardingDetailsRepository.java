package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.entities.EmployeeOnboardingDetails;

@Repository
public interface EmployeeOnboardingDetailsRepository extends JpaRepository<EmployeeOnboardingDetails, String>{

}
