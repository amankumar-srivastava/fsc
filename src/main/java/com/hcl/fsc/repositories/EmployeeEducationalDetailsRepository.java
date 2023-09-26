package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcl.fsc.entities.EmployeeEducationalDetails;

@Repository
public interface EmployeeEducationalDetailsRepository extends JpaRepository<EmployeeEducationalDetails, String> {

}
