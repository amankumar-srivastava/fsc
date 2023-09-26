package com.hcl.fsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.fsc.mastertables.CollegeTiering;
import com.hcl.fsc.mastertables.OfferedDesignation;

@Repository
public interface OfferedDesignationRepository extends JpaRepository<OfferedDesignation, String> {

	public OfferedDesignation getByKey(String key);

	public OfferedDesignation findByValue(String value);

	public OfferedDesignation findTopByOrderByUidDesc();
}
