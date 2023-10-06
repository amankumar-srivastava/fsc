package com.hcl.fsc.repositories.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.CustomerName;
import com.hcl.fsc.mastertables.HrL4;

@Repository
public interface CustomerNameRepository extends JpaRepository<CustomerName, Integer> {
	public CustomerName findTopByOrderByUidDesc();

	public boolean existsCustomerNameBycustomerName(String customerName);
	
	public CustomerName findBycustomerName(String customerName);
	
}
